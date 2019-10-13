package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.registry.*;
import parts.lost.calcsystem.types.Value;
import parts.lost.calcsystem.types.Operator;
import parts.lost.calcsystem.types.TreeType;

import java.util.*;
import java.util.regex.Pattern;

public class Calculate {

    private Registry registry;
    private String oldreg = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([a-zA-Z]+)|([*+\\-/%^])|([(])|([)])";
    private Pattern pattern;


    public Calculate() {
        registry = new Registry();
        initRegex();
    }

    public Calculate(Registry registry) {
        this.registry = registry;
        initRegex();
    }

    private void initRegex() {
        pattern = Pattern.compile(
                "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([*+\\-/%^()])|([^\\d()*+\\-/%^]+)");
    }

    private List<String> parseStringRegex(String string) {
        var matcher = pattern.matcher(string);

        List<String> groups = new ArrayList<>();
        try {
            while (matcher.find()) {
                groups.add(matcher.group());
            }
        } catch (StackOverflowError ex) {
            throw new RuntimeException("Incorrect syntax in string.", ex);
        }

        return groups;
    }


    private Flag[] parseTypes(List<String> strings) {
        Flag[] array = new Flag[strings.size()];
        Outer:
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            try {
                double value = Double.parseDouble(string);
                array[i] = new Flag(new Value(value));
            } catch (NumberFormatException | NullPointerException ex) {
                if (string.equals("(") || string.equals(")")) {
                    array[i] = new Flag(string);
                    continue;
                }
                for (Item item : registry) {
                    if (item.getIdentifier().equals(string)) {
                        array[i] = new Flag(item);
                        continue Outer;
                    }
                }
                throw new RuntimeException("Unknown operator found.");
            }
        }

        return array;
    }

    private Flag[] infixToPostfix(Flag[] flags) {
        List<Flag> output = new ArrayList<>(flags.length);

        Stack<Flag> stack = new Stack<>();

        for (Flag flag : flags) {
            if (flag.isNumber())
                output.add(flag);
            else if (flag.isOperator()) {
                while (!stack.empty() && stack.peek().isOperator() &&
                        ((OperatorItem) stack.peek().getObject()).getPriority().compareTo(((OperatorItem) flag.getObject()).getPriority()) >= 0) {
                    output.add(stack.pop());
                }
                stack.push(flag);
            } else if (flag.isOpenParentheses()) {
                stack.push(flag);
            } else if (!stack.empty() && flag.isCloseParentheses()) {
                while (!stack.empty() && !stack.peek().isOpenParentheses())
                    output.add(stack.pop());
                stack.pop();
            }
        }
        while (!stack.empty())
            output.add(stack.pop());

        return output.toArray(new Flag[0]);
    }

    private TreeType buildTree(Flag[] postFix) {
        Stack<TreeType> stack = new Stack<>();
        for (Flag flag : postFix) {
            if (flag.isNumber()) {
                stack.push(((Value) flag.getObject()));
            } else if (flag.isOperator()) {
                TreeType right = stack.pop();
                TreeType left = stack.pop();
                stack.push(new Operator(left, right, ((OperatorItem) flag.getObject()).getOperation()));
            }
        }
        return stack.pop();
    }

    public Calculation interpolate(String string) {
        string = string.replace(" ", "");

        List<String> groups = parseStringRegex(string);
        Flag[] flags = parseTypes(groups);
        Flag[] postFix = infixToPostfix(flags);


        //System.out.println(groups);
        //System.out.println(Arrays.toString(flags));

        return new Calculation(string, buildTree(postFix));
    }

    public double calculate(String string) {
        return interpolate(string).solve().getDouble();
    }

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        calculate.registry.add(new GeneratorItem("sin", Priority.ONE, 1, value -> new Value(Math.sin(value[0].getDouble()))));
        //calculate.interpolate("sin(4.54)+13*.4*13.039");
        //calculate.interpolate("300*233.32+sin(.32)*342432.43+.43/23423423434234234342342342324342342234234324324234234324234234324234+234234/3/sin(34)");
        //System.out.println("Result: " + calculate.interpolate("1 * (2 + 3) / 4").solve());
        //System.out.println("Result:" + calculate.interpolate("(34.4-.4)-4/30").solve());
        //System.out.println("Result: " + calculate.interpolate("4-3").solve());
        //calculate.interpolate("1 * (2 + 3) / 4");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter equation: ");
        String line = scanner.nextLine();
        while (!line.toLowerCase().equals("q")) {
            try {
                long time = System.nanoTime();
                double value = calculate.calculate(line);
                long end = System.nanoTime();
                System.out.println("Value: " + value);
                System.out.println("Runtime: " + ((end - time) / 1000000.0) + "ms");
                System.out.print("Enter equation: ");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            line = scanner.nextLine();
        }
    }
}

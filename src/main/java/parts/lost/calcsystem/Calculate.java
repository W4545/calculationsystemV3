package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.registry.*;
import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.registry.types.Item;
import parts.lost.calcsystem.registry.types.OperatorItem;
import parts.lost.calcsystem.types.Generator;
import parts.lost.calcsystem.types.Value;
import parts.lost.calcsystem.types.Operator;
import parts.lost.calcsystem.types.TreeType;

import java.util.*;
import java.util.regex.Pattern;

public class Calculate {

    private Registry registry;
    private String oldreg = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([a-zA-Z]+)|([*+\\-/%^])|([(])|([)])";
    private String oldreg2 = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([*+\\-/%^(),])|([^\\d()*+\\-/%^,]+)";
    private Pattern pattern;


    public Calculate() {
        registry = new Registry();
        initRegex();
    }

    public Calculate(Registry registry) {
        this.registry = registry;
        initRegex();
    }

    protected void initRegex() {
        pattern = Pattern.compile("(\\d+(?:[.]\\d*)?|[.]\\d+)|([*+\\-/%^])|([(),])|([^\\d()*+\\-/%^,.\\s]+)");
    }

    protected List<Flag> parseStringRegex(String string) {
        var matcher = pattern.matcher(string);

        List<Flag> groups = new ArrayList<>();
        try {
            while (matcher.find()) {
                String output1 = matcher.group(1);
                String output2 = matcher.group(2);
                String output3 = matcher.group(3);
                String output4 = matcher.group(4);

                if (output1 != null) {
                    // Matched a number
                    groups.add(new Flag(new Value(Double.parseDouble(output1))));
                } else if (output2 != null) {
                    // Matched an operator
                    for (Item item : registry) {
                        if (item.getIdentifier().equals(output2)) {
                            groups.add(new Flag(item));
                            break;
                        }
                    }
                } else if (output3 != null) // Found comma or parentheses
                    groups.add(new Flag(output3));
                else if (output4 != null) {
                    // Found generator
                    for (Item item : registry) {
                        if (item.getIdentifier().equals(output4)) {
                            groups.add(new Flag(item));
                            break;
                        }
                    }
                }
            }
        } catch (StackOverflowError | NumberFormatException ex) {
            throw new RuntimeException("Incorrect syntax in string.", ex);
        }

        return groups;
    }


    protected Flag[] parseTypes(List<String> strings) {
        Flag[] array = new Flag[strings.size()];
        Outer:
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            try {
                double value = Double.parseDouble(string);
                array[i] = new Flag(new Value(value));
            } catch (NumberFormatException | NullPointerException ex) {
                if (string.equals("(") || string.equals(")") || string.equals(",")) {
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

    protected TreeType[] buildGenerator(List<Flag> list) {
        List<TreeType> toOutput = new ArrayList<>();
        List<Flag> build = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).isGeneratorItem()) {
                int[] pos = outerParenthesis(list, i + 1);
                TreeType[] genTrees = buildGenerator(list.subList(pos[0], pos[1]));
                GeneratorItem item = (GeneratorItem) list.get(i).getObject();
                if (item.getArgumentCount() != -1 && genTrees.length != item.getArgumentCount())
                    throw new RuntimeException("Incorrect number of arguments passed to generator: " + item.getIdentifier());
                build.add(new Flag(new Generator(genTrees, item.getOperation())));
                i = pos[1];
            } else if (list.get(i).isComma()) {
                Flag[] postfix = infixToPostfix(build.toArray(new Flag[0]));
                toOutput.add(buildTree(postfix));
                build.clear();
            } else {
                build.add(list.get(i));
            }

        }
        if (build.size() > 0)
        {
            Flag[] postfix = infixToPostfix(build.toArray(new Flag[0]));
            toOutput.add(buildTree(postfix));
        }

        return toOutput.toArray(new TreeType[0]);
    }

    protected int[] outerParenthesis(List<Flag> flags, int start) {
        int openParenthesisPos = 0;
        int closedParenthesisPos = -1;
        if (start + 1 != flags.size() && flags.get(start).isOpenParentheses())
            openParenthesisPos = start;
        else
            throw new RuntimeException("Incorrect Generator formatting: \"(\" expected");
        int openParenthesesEncountered = 0;
        for (int k = start + 1; k < flags.size(); k++) {
            if (flags.get(k).isOpenParentheses())
                openParenthesesEncountered++;
            else if (flags.get(k).isCloseParentheses() && openParenthesesEncountered > 0)
                openParenthesesEncountered--;
            else if (flags.get(k).isCloseParentheses() && openParenthesesEncountered == 0) {
                closedParenthesisPos = k;
                break;
            }
        }

        if (closedParenthesisPos == -1)
            throw new RuntimeException("Invalid formatting: missing a closing parenthesis \")\"");

        return new int[] {openParenthesisPos, closedParenthesisPos};
    }

    protected Flag[] generatorParse(List<Flag> flags) {
        List<Flag> list = new ArrayList<>();
        try {
            for (int i = 0; i < flags.size(); i++) {
                if (flags.get(i).isGeneratorItem()) {
                    int[] pos = outerParenthesis(flags, i + 1);
                    TreeType[] genTrees = buildGenerator(flags.subList(pos[0] + 1, pos[1]));

                    GeneratorItem item = (GeneratorItem) flags.get(i).getObject();
                    if (item.getArgumentCount() != -1 && genTrees.length != item.getArgumentCount())
                        throw new RuntimeException("Incorrect number of arguments passed to generator: " + item.getIdentifier());

                    list.add(new Flag(new Generator(genTrees, item.getOperation())));
                    i = pos[1];
                }
                else
                    list.add(flags.get(i));

            }


        } catch (IndexOutOfBoundsException ex) {
            throw new RuntimeException("Incorrect Generator formatting", ex);
        }

        return list.toArray(new Flag[0]);
    }

    protected Flag[] infixToPostfix(Flag[] flags) {
        List<Flag> output = new ArrayList<>(flags.length);

        Stack<Flag> stack = new Stack<>();

        for (Flag flag : flags) {
            if (flag.isNumber() || flag.isGenerator())
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

    protected TreeType buildTree(Flag[] postFix) {
        Stack<TreeType> stack = new Stack<>();
        for (Flag flag : postFix) {
            if (flag.isNumber() || flag.isGenerator()) {
                stack.push((TreeType) flag.getObject());
            } else if (flag.isOperator()) {
                TreeType right = stack.pop();
                TreeType left = stack.pop();
                stack.push(new Operator(left, right, ((OperatorItem) flag.getObject()).getOperation()));
            }
        }
        return stack.pop();
    }

    public Calculation interpolate(String string) {

        List<Flag> groups = parseStringRegex(string);
        //Flag[] flags = parseTypes(groups);
        Flag[] generatorPass = generatorParse(groups);
        Flag[] postFix = infixToPostfix(generatorPass);


        //System.out.println(groups);
        //System.out.println(Arrays.toString(flags));

        return new Calculation(string, buildTree(postFix));
    }

    public double calculate(String string) {
        return interpolate(string).solve().getDouble();
    }

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        calculate.registry.add(new GeneratorItem("sin",1, value -> new Value(Math.sin(value[0].value().getDouble()))));
        calculate.registry.add(new GeneratorItem("max", -1, value -> {
            double max = value[0].value().getDouble();
            for (int i = 1; i < value.length; ++i) {
                double current = value[i].value().getDouble();
                if (current > max) {
                    max = current;
                }
            }
            return new Value(max);
        }));
        //calculate.interpolate("sin(4.54)+13*.4*13.039");
        //calculate.interpolate("300*233.32+sin(.32)*342432.43+.43/23423423434234234342342342324342342234234324324234234324234234324234+234234/3/sin(34)");
        //System.out.println("Result: " + calculate.interpolate("1 * (2 + 3) / 4").solve());
        //System.out.println("Result:" + calculate.interpolate("(34.4-.4)-4/30").solve());
        //System.out.println("Result: " + calculate.interpolate("4-3").solve());
        //calculate.interpolate("1 * (2 + 3) / 4");
        System.out.println(calculate.calculate("max(55+5*4, 44, 20, 2*5) + 5"));
        System.out.println(calculate.calculate("max(1, max(5, 7) + 1)"));
        calculate.interpolate("max(55+5*4, 44, 20, 2*5) + 5");
        calculate.interpolate("4 * sin(4)");
        calculate.interpolate("45*5");
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

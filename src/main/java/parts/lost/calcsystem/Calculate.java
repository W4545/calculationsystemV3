package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.registry.*;
import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.registry.types.OperatorItem;
import parts.lost.calcsystem.types.Generator;
import parts.lost.calcsystem.types.Value;
import parts.lost.calcsystem.types.Operator;
import parts.lost.calcsystem.types.TreeType;

import java.util.*;

public class Calculate {

    private Registry registry;
    private StringParser parseStringRegex;


    public Calculate() {
        registry = new Registry();
        parseStringRegex = new StringParser();
    }

    public Calculate(Registry registry) {
        this.registry = registry;
        parseStringRegex = new StringParser();
    }

    protected TreeType[] buildGenerator(List<Flag> list) {
        List<TreeType> toOutput = new ArrayList<>();
        List<Flag> build = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).isGeneratorItem()) {
                int[] pos = outerParenthesis(list, i + 1);
                TreeType[] genTrees = buildGenerator(list.subList(pos[0], pos[1]));
                genSetup(list, build, i, genTrees);
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

    /**
     *
     * @param list list of complete expression (may be a partial expression, but must contain the complete generator
     * @param build list being used to build the new expression with the properly set up Generator object
     * @param i Position of GeneratorItem Flag in list
     * @param genTrees An array of the expressions (represented as {@link TreeType})
     */
    private void genSetup(List<Flag> list, List<Flag> build, int i, TreeType[] genTrees) {
        GeneratorItem item = (GeneratorItem) list.get(i).getObject();
        if (item.getArgumentCount() != -1 && genTrees.length != item.getArgumentCount())
            throw new RuntimeException("Incorrect number of arguments passed to generator: " + item.getIdentifier());
        build.add(new Flag(new Generator(genTrees, item.getOperation())));
    }

    protected int[] outerParenthesis(List<Flag> flags, int start) {
        int openParenthesisPos;
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
                    genSetup(flags, list, i, genTrees);
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

        List<Flag> groups = parseStringRegex.parseStringRegex(string, registry);
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


        //System.out.println(calculate.calculate("max(55+5*4, 44, 20, 2*5) + 5"));
        //System.out.println(calculate.calculate("max(1, max(5, 7) + 1)"));
        calculate.interpolate("max(55+5*4, 44, 20, 2*5) + 5");
        calculate.interpolate("4 * sin(4)");
        calculate.interpolate("45*5");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter equation: ");
                String line = scanner.nextLine();

                if (line.toLowerCase().equals("q"))
                    break;

                long time = System.nanoTime();
                double value = calculate.calculate(line);
                long end = System.nanoTime();
                System.out.println("Value: " + value);
                System.out.println("Runtime: " + ((end - time) / 1000000.0) + "ms");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

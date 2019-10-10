package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.registry.*;
import parts.lost.calcsystem.types.Number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                array[i] = new Flag(new Number(value));
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

    public Calculation interpolate(String string) {
        long time = System.nanoTime();
        string = string.replace(" ", "");

        List<String> groups = parseStringRegex(string);
        Flag[] flags = parseTypes(groups);

        long finish = System.nanoTime();

        System.out.println(groups);
        System.out.println(Arrays.toString(flags));
        System.out.println((finish - time) / 1000000.0);

        return null;
    }

    public double calculate(String string) {
        return interpolate(string).solve().getDouble();
    }

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        calculate.registry.add(new GeneratorItem("sin", Priority.ONE, 1, value -> new Number(Math.sin(value[0].getDouble()))));
        calculate.interpolate("sin(4.54)+13*.4*13.039");
        calculate.interpolate("300*233.32+sin(.32)*342432.43+.43/23423423434234234342342342324342342234234324324234234324234234324234+234234/3/sin(34)");
    }
}

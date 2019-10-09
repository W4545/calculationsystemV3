package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.registry.Registry;

import java.util.ArrayList;
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

    public Calculation interpolate(String string) {
        long time = System.nanoTime();
        string = string.replace(" ", "");

        var matcher = pattern.matcher(string);

        List<String> groups = new ArrayList<>();
        try {
            while (matcher.find()) {
                groups.add(matcher.group());
            }
        } catch (StackOverflowError ex) {
            throw new RuntimeException("Incorrect syntax in string.", ex);
        }



        long finish = System.nanoTime();

        System.out.println(groups);
        System.out.println((finish - time) / 1000000.0);

        return null;
    }

    public double calculate(String string) {
        return interpolate(string).solve().getDouble();
    }

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        calculate.interpolate("sin(4.54)+13*.4*13.039");
        calculate.interpolate("300*233.32+sin(.32)*342432.43+.43/23423423434234234342342342324342342234234324324234234324234234324234+234234/3/sin(34)");
    }
}

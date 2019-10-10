package parts.lost.calcsystem.registry;

// Name: Jack Young
// Date: 10/8/2019

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @version 0.5.0
 */
public class Registry implements Iterable<Item> {

    private Set<OperatorItem> operators;
    private Set<GeneratorItem> generators;
    private Set<ConstantItem> constants;
    private Set<UnaryItem> unaryOperators;

    public Registry() {
        init();
        loadDefaults();
    }

    public Registry(boolean loadDefaults) {
        init();
        if (loadDefaults)
            loadDefaults();
    }

    private void init() {
        operators = new HashSet<>(8);
        generators = new HashSet<>(8);
        constants = new HashSet<>(4);
        unaryOperators = new HashSet<>(2);
    }

    public void loadDefaults() {
        operators.add(Defaults.ADDITION);
        operators.add(Defaults.DIVISION);
        operators.add(Defaults.MULTIPLICATION);
        operators.add(Defaults.SUBTRACTION);

        unaryOperators.add(Defaults.NEGATION);
    }

    public boolean add(OperatorItem item) {
        return operators.add(item);
    }

    public boolean add(GeneratorItem item) {
        return generators.add(item);
    }

    public boolean add(UnaryItem item) {
        return unaryOperators.add(item);
    }

    public boolean add(ConstantItem item) {
        return constants.add(item);
    }

    public Set<OperatorItem> getOperators() {
        return operators;
    }

    public Set<GeneratorItem> getGenerators() {
        return generators;
    }

    public Set<ConstantItem> getConstants() {
        return constants;
    }

    public Set<UnaryItem> getUnaryOperators() {
        return unaryOperators;
    }

    public boolean contains(Item item) {
        return operators.contains(item) || generators.contains(item) ||
                constants.contains(item) || unaryOperators.contains(item);
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Iterator<? extends Item> constantIterator = constants.iterator();
            private Iterator<? extends Item> operatorIterator = operators.iterator();
            private Iterator<? extends Item> generatorIterator = generators.iterator();
            private byte current = 0;

            @Override
            public boolean hasNext() {
                if (operatorIterator.hasNext()) {
                    current = 0;
                    return true;
                } else if (generatorIterator.hasNext()) {
                    current = 1;
                    return true;
                } else if (constantIterator.hasNext()) {
                    current = 3;
                    return true;
                } else
                    return false;

            }

            @Override
            public Item next() {
                if (current == 0)
                    return operatorIterator.next();
                else if (current == 1)
                    return generatorIterator.next();
                else
                    return constantIterator.next();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Item> action) {

    }

    @Override
    public Spliterator<Item> spliterator() {
        return null;
    }
}

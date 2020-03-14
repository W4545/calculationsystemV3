package parts.lost.calcsystem.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.registry.types.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @version 0.5.2
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
        add(Defaults.ADDITION);
        add(Defaults.DIVISION);
        add(Defaults.MULTIPLICATION);
        add(Defaults.SUBTRACTION);
        add(Defaults.EXPONENT);
        add(Defaults.NEGATION);
        add(Defaults.COSINE_GENERATOR);
        add(Defaults.SINE_GENERATOR);
        add(Defaults.PI);
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
        return new Iterator<>() {
            @SuppressWarnings("unchecked")
            private Iterator<? extends Item>[] iterators = new Iterator[4];
            {
                iterators[0] = operators.iterator();
                iterators[1] = generators.iterator();
                iterators[2] = constants.iterator();
                iterators[3] = unaryOperators.iterator();
            }

            private int current = 0;

            @Override
            public boolean hasNext() {
                for (int i = current; i < iterators.length; ++i) {
                    if (iterators[i].hasNext()) {
                        current = i;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Item next() {
                return iterators[current].next();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Item> action) {
        for (OperatorItem item : operators)
            action.accept(item);
        for (GeneratorItem item : generators)
            action.accept(item);
        for (ConstantItem item : constants)
            action.accept(item);
        for (UnaryItem item : unaryOperators)
            action.accept(item);
    }

    @Override
    public Spliterator<Item> spliterator() {
        return null;
    }
}

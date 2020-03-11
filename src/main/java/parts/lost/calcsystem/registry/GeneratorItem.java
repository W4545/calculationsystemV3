package parts.lost.calcsystem.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;
import parts.lost.calcsystem.types.GenOperation;

public class GeneratorItem extends Item {

    private GenOperation operation;
    private int argumentCount;

    public GeneratorItem(String identifier, int argumentCount, GenOperation operation) {
        this.identifier = identifier;
        this.priority = Priority.ONE;
        this.operation = operation;
        this.argumentCount = argumentCount;
    }

    public GenOperation getOperation() {
        return operation;
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    @Override
    public String toString() {
        return "GeneratorItem{" +
                "argumentCount=" + argumentCount +
                ", identifier='" + identifier + '\'' +
                ", priority=" + priority +
                '}';
    }
}

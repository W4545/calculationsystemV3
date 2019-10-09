package parts.lost.calcsystem.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;
import parts.lost.calcsystem.types.GenOperation;

public class GeneratorItem extends Item {

    private GenOperation operation;

    public GeneratorItem(String identifier, Priority priority, GenOperation operation) {
        this.identifier = identifier;
        this.priority = priority;
        this.operation = operation;
    }

    public GenOperation getOperation() {
        return operation;
    }
}

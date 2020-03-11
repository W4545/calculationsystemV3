package parts.lost.calcsystem.registry.types;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;
import parts.lost.calcsystem.types.operations.Operation;

public class OperatorItem extends Item {

    private Operation operation;

    public OperatorItem(String identifier, Priority priority, Operation operation) {
        this.identifier = identifier;
        this.priority = priority;
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "OperatorItem{" +

                "identifier='" + identifier + '\'' +
                ", priority=" + priority +
                '}';
    }
}

package parts.lost.calcsystem.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;
import parts.lost.calcsystem.types.UnaryOperation;

public class UnaryItem extends Item {

    private UnaryOperation operation;

    public UnaryItem(String identifier, Priority priority, UnaryOperation operation) {
        this.identifier = identifier;
        this.priority = priority;
        this.operation = operation;
    }

    public UnaryOperation getOperation() {
        return operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnaryItem)) return false;

        UnaryItem unaryItem = (UnaryItem) o;

        if (!identifier.equals(unaryItem.identifier)) return false;
        return priority == unaryItem.priority;
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + priority.hashCode();
        return result;
    }
}

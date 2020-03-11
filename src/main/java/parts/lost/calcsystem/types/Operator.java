package parts.lost.calcsystem.types;

import parts.lost.calcsystem.types.operations.Operation;

public class Operator implements TreeType {

    private TreeType left, right;
    private Operation operation;

    public Operator(TreeType left, TreeType right, Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public Value value() {
        return operation.operation(left.value(), right.value());
    }

    @Override
    public String toString() {
        return "" + left + "OP" + right;
    }
}

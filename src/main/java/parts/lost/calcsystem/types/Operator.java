package parts.lost.calcsystem.types;

public class Operator implements TreeType {

    private TreeType left, right;
    private Operation operation;

    public Operator(TreeType left, TreeType right, Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public Number value() {
        return operation.operation(left.value(), right.value());
    }
}

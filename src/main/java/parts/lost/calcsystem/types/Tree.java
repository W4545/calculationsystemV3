package parts.lost.calcsystem.types;

public class Tree extends Node {

    private Node left;
    private Node right;
    private Operation operation;

    public Tree(Node left, Node right, Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public Number resolve() {
        return operation.operation(left.resolve(), right.resolve());
    }
}

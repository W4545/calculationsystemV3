package parts.lost.calcsystem.tree;

import parts.lost.calcsystem.types.Number;
import parts.lost.calcsystem.types.Operation;

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

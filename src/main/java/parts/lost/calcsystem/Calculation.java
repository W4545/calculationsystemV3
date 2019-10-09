package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.tree.Node;
import parts.lost.calcsystem.types.Number;

public class Calculation {

    private Node tree;
    private String representation;

    Calculation(String representation, Node tree) {
        this.representation = representation;
        this.tree = tree;
    }

    public String getCalculation() {
        return representation;
    }

    public Number solve() {
        return tree.resolve();
    }
}

package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.types.Number;
import parts.lost.calcsystem.types.TreeType;

public class Calculation {

    private TreeType tree;
    private String representation;

    Calculation(String representation, TreeType tree) {
        this.representation = representation;
        this.tree = tree;
    }

    public String getCalculation() {
        return representation;
    }

    public Number solve() {
        return tree.value();
    }
}

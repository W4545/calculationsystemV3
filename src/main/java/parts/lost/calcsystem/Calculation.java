package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.types.Value;
import parts.lost.calcsystem.types.TreeType;

public class Calculation {

    private TreeType tree;
    private String representation;
    private Value value;

    Calculation(String representation, TreeType tree) {
        this.representation = representation;
        this.tree = tree;
        this.value = null;
    }

    public String getCalculation() {
        return representation;
    }

    public Value solve() {
        if (value == null) {
            value = tree.value();
            return value;
        } else
            return value;
    }
}

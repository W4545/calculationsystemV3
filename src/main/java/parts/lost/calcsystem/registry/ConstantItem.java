package parts.lost.calcsystem.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;

public class ConstantItem extends Item {

    private Number value;

    public ConstantItem(String identifier, Number value) {
        this.identifier = identifier;
        this.priority = Priority.ONE;
        this.value = value;
    }

    public Number getValue() {
        return value;
    }
}

package parts.lost.calcsystem.registry.types;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;

public abstract class Item {
    protected String identifier;
    protected Priority priority;

    protected Item() {

    }

    public String getIdentifier() {
        return identifier;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (!identifier.equals(item.identifier)) return false;
        return priority == item.priority;
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + priority.hashCode();
        return result;
    }
}

package parts.lost.calcsystem.types;

public class Node {

    private TreeType object;

    public Node(TreeType object) {
        this.object = object;
    }

    Node() {
        object = null;
    }

    public Number resolve() {
        if (object != null)
            return object.value();
        else
            throw new RuntimeException("Error resolving object. Object is null.");
    }
}

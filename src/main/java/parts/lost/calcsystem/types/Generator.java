package parts.lost.calcsystem.types;

public class Generator implements TreeType {

    private TreeType[] array;
    private GenOperation operation;

    public Generator(TreeType[] array, GenOperation operation) {
        this.array = array;
        this.operation = operation;
    }

    @Override
    public Number value() {
        Number[] resolvedArray = new Number[array.length];

        for (int i = 0; i < array.length; i++)
            resolvedArray[i] = array[i].value();

        return operation.operation(resolvedArray);
    }
}

package parts.lost.calcsystem.types.operations.defaults;

// Name: Jack Young
// Date: 3/13/2020

import parts.lost.calcsystem.types.TreeType;
import parts.lost.calcsystem.types.Value;
import parts.lost.calcsystem.types.operations.GenOperation;

public class SineGenOperation implements GenOperation {

    public static final SineGenOperation SINE_GEN_OPERATION = new SineGenOperation();

    @Override
    public Value operation(TreeType[] array) {
        return new Value(Math.sin(array[0].value().getDouble()));
    }

    @Override
    public String toString() {
        return "sin";
    }
}

package parts.lost.calcsystem.types.operations.defaults;

// Name: Jack Young
// Date: 3/13/2020

import parts.lost.calcsystem.types.TreeType;
import parts.lost.calcsystem.types.Value;
import parts.lost.calcsystem.types.operations.GenOperation;

public class CosineGenOperation implements GenOperation {

    public final static CosineGenOperation COSINE_GEN_OPERATION = new CosineGenOperation();

    @Override
    public Value operation(TreeType[] array) {
        return new Value(Math.cos(array[0].value().getDouble()));
    }
}

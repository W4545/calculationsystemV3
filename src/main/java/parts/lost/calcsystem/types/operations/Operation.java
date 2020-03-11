package parts.lost.calcsystem.types.operations;

import parts.lost.calcsystem.types.Value;

public interface Operation {

    Value operation(Value left, Value right);
}

package parts.lost.calcsystem.types;

public interface Operation {

    Value operation(Value left, Value right);
}

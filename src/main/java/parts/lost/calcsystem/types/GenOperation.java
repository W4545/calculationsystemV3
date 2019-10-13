package parts.lost.calcsystem.types;

public interface GenOperation {

    /**
     *
     * @param array Value objects corresponding to those passed into generator by user
     * @return A number
     */
    Value operation(Value[] array);
}

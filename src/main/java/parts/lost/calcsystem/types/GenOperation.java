package parts.lost.calcsystem.types;

public interface GenOperation {

    /**
     *
     * @param array Number objects corresponding to those passed into generator by user
     * @return A number
     */
    Number operation(Number[] array);
}

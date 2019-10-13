package parts.lost.calcsystem.types;

/**
 *
 * @version 1.0.0
 * @since 0.0
 */
public final class Value implements TreeType {

    private double value;

    public Value(double value) {
        this.value = value;
    }

    public Value(int value) {
        this.value = value;
    }

    public Value() {
        this.value = 0;
    }

    @Override
    public Value value() {
        return this;
    }

    public double getDouble() {
        return value;
    }

    public Value negate() {
        value = -value;
        return this;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}

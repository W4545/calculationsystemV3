package parts.lost.calcsystem.types;

import parts.lost.calcsystem.Priority;

/**
 *
 * @version 1.0.0
 * @since 0.0
 */
public final class Number implements TreeType {

    private double value;

    public Number(double value) {
        this.value = value;
    }

    public Number(int value) {
        this.value = value;
    }

    public Number() {
        this.value = 0;
    }

    @Override
    public Number value() {
        return this;
    }

    public double getDouble() {
        return value;
    }

    public Number negate() {
        value = -value;
        return this;
    }
}

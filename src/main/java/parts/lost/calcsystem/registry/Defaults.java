package parts.lost.calcsystem.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;
import parts.lost.calcsystem.types.Number;

public class Defaults {

    public static final OperatorItem ADDITION = new OperatorItem("+", Priority.THREE,
            (left, right) -> new Number(left.getDouble() + right.getDouble()));

    public static final OperatorItem SUBTRACTION = new OperatorItem("-", Priority.THREE,
            (left, right) -> new Number(left.getDouble() - right.getDouble()));

    public static final OperatorItem MULTIPLICATION = new OperatorItem("*", Priority.FIVE,
            (left, right) -> new Number(left.getDouble() * right.getDouble()));

    public static final OperatorItem DIVISION = new OperatorItem("/", Priority.FIVE,
            (left, right) -> new Number(left.getDouble()  / right.getDouble()));

    public static final UnaryItem NEGATION = new UnaryItem("-", Priority.NINE, Number::negate);
}

package parts.lost.calcsystem;

/**
 * Represents the prioritization of the various operators, generators, and numbers.
 * Higher numbers are higher priority.
 * @version 0.5.0
 * @since 0.0
 */
public enum Priority {
	/**
	 * Reserved for ordinary numbers and generators
	 */
	ONE,
	TWO,
	/**
	 * Reserved for operators that execute on the same level as addition and subtraction
	 */
	THREE,
	FOUR,

	/**
	 * Reserved for operators that execute on the same level as multiplication and subtraction
	 */
	FIVE,
	SIX,
	SEVEN,
	EIGHT,

	/**
	 * Reserved for unary operators that execute on the same level as negation
	 */
	NINE,
	TEN
}

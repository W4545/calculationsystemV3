package parts.lost.calculationsystem.core.exceptions;

// Name: Jack Young
// Date: 3/21/2020

import parts.lost.calculationsystem.core.State;

public class CalculationStringParsingException extends CalculationExpressionFormatException {
	private String unknown;

	public CalculationStringParsingException(State state, String unknown) {
		super(state, String.format("An unknown element \"%s\" was found in the given expression: %s", unknown, state.getExpression()));
		this.unknown = unknown;
	}

	protected CalculationStringParsingException(State state, String message, Exception cause) {
		super(state, message, cause);
	}

//	protected CalculationStringParsingException(State state, String message) {
//		super(state, message);
//	}

	public String getUnknown() {
		return unknown;
	}
}

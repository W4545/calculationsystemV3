package parts.lost.calculationsystem.core.exceptions;

// Name: Jack Young
// Date: 3/21/2020

import parts.lost.calculationsystem.core.State;

public class CalculationExpressionFormatException extends CalculationException {

	public CalculationExpressionFormatException(State state, Exception cause) {
		super(state, String.format("An unknown formatting error occurred in provided expression: %s", state.getExpression()), cause);
	}

	public CalculationExpressionFormatException(State state) {
		super(state, String.format("An unknown formatting error occurred in provided expression: %s", state.getExpression()));
	}

	protected CalculationExpressionFormatException(State state, String message, Exception cause) {
		super(state, message, cause);
	}

	protected CalculationExpressionFormatException(State state, String message) {
		super(state, message);
	}
}

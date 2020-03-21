package parts.lost.calculationsystem.core.exceptions;


import parts.lost.calculationsystem.core.State;

public class CalculationException extends RuntimeException {

	protected State state;

	public CalculationException(State state, String message, Exception cause) {
		super(message, cause);
		this.state = state;
	}

	public CalculationException(State state, String message) {
		super(message);
		this.state = state;
	}

	protected CalculationException(String message, Exception cause) {
		super(message, cause);
	}

	protected CalculationException(String message) {
		super(message);
	}


}

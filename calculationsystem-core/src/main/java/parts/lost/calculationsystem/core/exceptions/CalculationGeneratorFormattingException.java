package parts.lost.calculationsystem.core.exceptions;

// Name: Jack Young
// Date: 3/21/2020

public class CalculationGeneratorFormattingException extends CalculationException {
	public CalculationGeneratorFormattingException(String message, Exception cause) {
		super(String.format("Incorrect generator formatting: %s", message), cause);
	}

	public CalculationGeneratorFormattingException(String message) {
		super(String.format("Incorrect generator formatting: %s", message));
	}
}

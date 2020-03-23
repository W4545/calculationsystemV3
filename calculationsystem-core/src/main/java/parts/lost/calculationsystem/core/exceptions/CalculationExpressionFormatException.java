/*
 * Copyright 2020 Jack Young
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package parts.lost.calculationsystem.core.exceptions;

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

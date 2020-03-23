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

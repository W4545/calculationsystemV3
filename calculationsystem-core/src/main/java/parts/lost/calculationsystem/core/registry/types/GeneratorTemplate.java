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
package parts.lost.calculationsystem.core.registry.types;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calculationsystem.core.Priority;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class GeneratorTemplate extends Template {

	private GenOperation operation;
	private int argumentCount;

	public GeneratorTemplate(String identifier, int argumentCount, GenOperation operation) {
		this.identifier = identifier;
		this.priority = Priority.ONE;
		this.operation = operation;
		this.argumentCount = argumentCount;
	}

	public GenOperation getOperation() {
		return operation;
	}

	public int getArgumentCount() {
		return argumentCount;
	}

	@Override
	public String toString() {
		return "GeneratorItem{" +
				"argumentCount=" + argumentCount +
				", identifier='" + identifier + '\'' +
				", priority=" + priority +
				'}';
	}
}

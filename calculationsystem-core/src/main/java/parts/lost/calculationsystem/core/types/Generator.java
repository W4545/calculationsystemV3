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
package parts.lost.calculationsystem.core.types;

import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class Generator implements TreeType {

	private final TreeType[] array;
	private final GenOperation operation;

	public Generator(TreeType[] array, GenOperation operation) {
		this.array = array;
		this.operation = operation;
	}

	@Override
	public Value value() {
		Value[] resolvedArray = new Value[array.length];

		for (int i = 0; i < array.length; i++)
			resolvedArray[i] = array[i].value();

		return operation.operation(resolvedArray);
	}
}

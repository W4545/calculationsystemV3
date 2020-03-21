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

import parts.lost.calculationsystem.core.types.operations.Operation;

public class Operator implements TreeType {

	private TreeType left, right;
	private Operation operation;

	public Operator(TreeType left, TreeType right, Operation operation) {
		this.left = left;
		this.right = right;
		this.operation = operation;
	}

	@Override
	public Value value() {
		return operation.operation(left.value(), right.value());
	}

	@Override
	public String toString() {
		return "" + left + "OP" + right;
	}
}

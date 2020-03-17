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
package parts.lost.calcsystem.registry.types;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;
import parts.lost.calcsystem.types.operations.Operation;

public class OperatorItem extends Item {

	private Operation operation;

	public OperatorItem(String identifier, Priority priority, Operation operation) {
		this.identifier = identifier;
		this.priority = priority;
		this.operation = operation;
	}

	public Operation getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		return "OperatorItem{" +

				"identifier='" + identifier + '\'' +
				", priority=" + priority +
				'}';
	}
}

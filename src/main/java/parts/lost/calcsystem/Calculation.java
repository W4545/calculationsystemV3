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
package parts.lost.calcsystem;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.types.Value;
import parts.lost.calcsystem.types.TreeType;

public class Calculation {

	private TreeType tree;
	private String representation;
	private Value value;

	Calculation(String representation, TreeType tree) {
		this.representation = representation;
		this.tree = tree;
		this.value = null;
	}

	public String getCalculation() {
		return representation;
	}

	public Value solve() {
		if (value == null)
			value = tree.value();
		return value;
	}
}

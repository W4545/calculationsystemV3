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

public abstract class Item {
	protected String identifier;
	protected Priority priority;

	protected Item() {

	}

	public String getIdentifier() {
		return identifier;
	}

	public Priority getPriority() {
		return priority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item)) return false;

		Item item = (Item) o;

		if (!identifier.equals(item.identifier)) return false;
		return priority == item.priority;
	}

	@Override
	public int hashCode() {
		int result = identifier.hashCode();
		result = 31 * result + priority.hashCode();
		return result;
	}
}

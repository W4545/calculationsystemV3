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

import java.util.Objects;

/**
 *
 * @version 1.0.0
 * @since 0.0
 */
public final class Value implements TreeType {

	private double value;

	public Value(double value) {
		this.value = value;
	}

	public Value(int value) {
		this.value = value;
	}

	public Value() {
		this.value = 0;
	}

	@Override
	public Value value() {
		return this;
	}

	public double getDouble() {
		return value;
	}

	public Value negate() {
		value = -value;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Value value1 = (Value) o;
		return Double.compare(value1.value, value) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}
}

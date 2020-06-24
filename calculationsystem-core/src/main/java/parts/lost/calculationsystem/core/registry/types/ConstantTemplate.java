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

import parts.lost.calculationsystem.core.Flag;
import parts.lost.calculationsystem.core.Priority;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.core.types.Constant;

import java.util.Objects;

public class ConstantTemplate extends Template {

	private final Constant constant;

	public ConstantTemplate(String identifier, Value value) {
		super(identifier, Priority.ONE);
		this.constant = new Constant(value);
	}

	public Constant getConstant() {
		return constant;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ConstantTemplate that = (ConstantTemplate) o;
		return getConstant().equals(that.getConstant());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getConstant());
	}

	@Override
	public Flag generateFlag() {
		return new Flag(this);
	}
}

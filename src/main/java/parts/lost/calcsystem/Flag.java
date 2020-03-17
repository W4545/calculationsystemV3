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

import parts.lost.calcsystem.registry.types.ConstantItem;
import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.registry.types.OperatorItem;
import parts.lost.calcsystem.types.Generator;
import parts.lost.calcsystem.types.Value;

public class Flag {

	private Object object;

	public Flag(Object object) {
		this.object = object;
	}

	public boolean isNumber() {
		return object instanceof Value;
	}

	public boolean isOperator() {
		return object instanceof OperatorItem;
	}

	public boolean isGeneratorItem() {
		return object instanceof GeneratorItem;
	}

	public boolean isOpenParentheses() {
		return object.equals("(");
	}

	public boolean isCloseParentheses() {
		return object.equals(")");
	}

	public boolean isComma() {
		return object.equals(",");
	}

	public boolean isGenerator() {
		return object instanceof Generator;
	}

	public boolean isConstant() {
		return object instanceof ConstantItem;
	}

	public Object getObject() {
		return object;
	}

	@Override
	public String toString() {
		return "Flag{" +
				object +
				'}';
	}
}

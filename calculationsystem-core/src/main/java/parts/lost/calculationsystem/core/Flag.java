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
package parts.lost.calculationsystem.core;

import parts.lost.calculationsystem.core.registry.types.ConstantTemplate;
import parts.lost.calculationsystem.core.registry.types.GeneratorTemplate;
import parts.lost.calculationsystem.core.registry.types.OperatorTemplate;
import parts.lost.calculationsystem.core.registry.types.UnaryTemplate;
import parts.lost.calculationsystem.core.types.Generator;
import parts.lost.calculationsystem.core.types.Value;

public class Flag {

	private final Object object;

	public Flag(Object object) {
		this.object = object;
	}

	public boolean isNumber() {
		return object instanceof Value;
	}

	public boolean isOperator() {
		return object instanceof OperatorTemplate;
	}

	public boolean isGeneratorItem() {
		return object instanceof GeneratorTemplate;
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
		return object instanceof ConstantTemplate;
	}

	public boolean isUnaryOperator() {
		return object instanceof UnaryTemplate;
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject() {
		return (T) object;
	}

	@Override
	public String toString() {
		return "Flag{" +
				object +
				'}';
	}
}

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
package parts.lost.calculationsystem.core.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calculationsystem.core.Priority;
import parts.lost.calculationsystem.core.registry.defaults.generators.CosineGeneratorItem;
import parts.lost.calculationsystem.core.registry.defaults.generators.SineGeneratorItem;
import parts.lost.calculationsystem.core.registry.types.ConstantItem;
import parts.lost.calculationsystem.core.registry.types.GeneratorItem;
import parts.lost.calculationsystem.core.registry.types.OperatorItem;
import parts.lost.calculationsystem.core.registry.types.UnaryItem;
import parts.lost.calculationsystem.core.types.Value;

public class Defaults {

	public static final OperatorItem ADDITION = new OperatorItem("+", Priority.THREE,
			(left, right) -> new Value(left.getDouble() + right.getDouble()));

	public static final OperatorItem SUBTRACTION = new OperatorItem("-", Priority.THREE,
			(left, right) -> new Value(left.getDouble() - right.getDouble()));

	public static final OperatorItem MULTIPLICATION = new OperatorItem("*", Priority.FIVE,
			(left, right) -> new Value(left.getDouble() * right.getDouble()));

	public static final OperatorItem DIVISION = new OperatorItem("/", Priority.FIVE,
			(left, right) -> new Value(left.getDouble()  / right.getDouble()));

	public static final OperatorItem EXPONENT = new OperatorItem("^", Priority.SIX,
			(left, right) -> new Value(Math.pow(left.getDouble(), right.getDouble())));

	public static final UnaryItem NEGATION = new UnaryItem("-", Priority.NINE, Value::negate);

	public static final UnaryItem POSITIVE = new UnaryItem("+", Priority.NINE,
			(value -> new Value(Math.abs(value.getDouble()))));

	public static final GeneratorItem COSINE_GENERATOR = new CosineGeneratorItem();

	public static final GeneratorItem SINE_GENERATOR = new SineGeneratorItem();

	public static final ConstantItem PI = new ConstantItem("pi", new Value(Math.PI));
}

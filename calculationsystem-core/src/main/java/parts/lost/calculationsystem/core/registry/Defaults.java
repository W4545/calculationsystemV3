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
import parts.lost.calculationsystem.core.registry.defaults.generators.SqrtGeneratorItem;
import parts.lost.calculationsystem.core.registry.defaults.modes.RadiansDegreesMode;
import parts.lost.calculationsystem.core.registry.defaults.modes.enums.RD;
import parts.lost.calculationsystem.core.registry.types.ConstantItem;
import parts.lost.calculationsystem.core.registry.types.OperatorItem;
import parts.lost.calculationsystem.core.registry.types.UnaryItem;
import parts.lost.calculationsystem.core.types.Value;

public final class Defaults {

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

	public static final RadiansDegreesMode RADIANS_DEGREES_MODE = new RadiansDegreesMode(RD.RADIANS);

	public static final CosineGeneratorItem COSINE_GENERATOR_ITEM = new CosineGeneratorItem();

	public static final SineGeneratorItem SINE_GENERATOR_ITEM = new SineGeneratorItem();

	public static final SqrtGeneratorItem SQRT_GENERATOR_ITEM = new SqrtGeneratorItem();

	public static final ConstantItem PI = new ConstantItem("pi", new Value(Math.PI));

	static {
		COSINE_GENERATOR_ITEM.setMode(RADIANS_DEGREES_MODE);
		SINE_GENERATOR_ITEM.setMode(RADIANS_DEGREES_MODE);
	}

	public static void load_defaults(Registry registry) {
		registry.add(ADDITION);
		registry.add(DIVISION);
		registry.add(MULTIPLICATION);
		registry.add(SUBTRACTION);
		registry.add(EXPONENT);
		registry.add(NEGATION);
		registry.add(POSITIVE);
		registry.add(COSINE_GENERATOR_ITEM);
		registry.add(SINE_GENERATOR_ITEM);
		registry.add(PI);
		registry.add(SQRT_GENERATOR_ITEM);
	}

	private Defaults() {

	}
}

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
import parts.lost.calculationsystem.core.registry.defaults.generators.*;
import parts.lost.calculationsystem.core.registry.defaults.modes.RadiansDegreesMode;
import parts.lost.calculationsystem.core.registry.defaults.modes.enums.RD;
import parts.lost.calculationsystem.core.registry.types.ConstantTemplate;
import parts.lost.calculationsystem.core.registry.types.OperatorTemplate;
import parts.lost.calculationsystem.core.registry.types.UnaryTemplate;
import parts.lost.calculationsystem.core.types.Value;

public final class Defaults {

	public static final OperatorTemplate ADDITION = new OperatorTemplate("+", Priority.THREE,
			(left, right) -> new Value(left.getDouble() + right.getDouble()));

	public static final OperatorTemplate SUBTRACTION = new OperatorTemplate("-", Priority.THREE,
			(left, right) -> new Value(left.getDouble() - right.getDouble()));

	public static final OperatorTemplate MULTIPLICATION = new OperatorTemplate("*", Priority.FIVE,
			(left, right) -> new Value(left.getDouble() * right.getDouble()));

	public static final OperatorTemplate DIVISION = new OperatorTemplate("/", Priority.FIVE,
			(left, right) -> new Value(left.getDouble()  / right.getDouble()));

	public static final OperatorTemplate EXPONENT = new OperatorTemplate("^", Priority.SIX,
			(left, right) -> new Value(Math.pow(left.getDouble(), right.getDouble())));

	public static final UnaryTemplate NEGATION = new UnaryTemplate("-", Priority.NINE, Value::negate);

	public static final UnaryTemplate POSITIVE = new UnaryTemplate("+", Priority.NINE,
			(value -> new Value(Math.abs(value.getDouble()))));

	public static final RadiansDegreesMode RADIANS_DEGREES_MODE = new RadiansDegreesMode(RD.RADIANS);

	public static final CosineGeneratorTemplate COSINE_GENERATOR_ITEM = new CosineGeneratorTemplate();

	public static final ArcCosineGeneratorTemplate ARC_COSINE_GENERATOR_ITEM = new ArcCosineGeneratorTemplate();

	public static final SineGeneratorTemplate SINE_GENERATOR_ITEM = new SineGeneratorTemplate();

	public static final ArcSineGeneratorTemplate ARC_SINE_GENERATOR_ITEM = new ArcSineGeneratorTemplate();

	public static final SqrtGeneratorTemplate SQRT_GENERATOR_ITEM = new SqrtGeneratorTemplate();

	public static final ConstantTemplate PI = new ConstantTemplate("pi", new Value(Math.PI));

	static {
		COSINE_GENERATOR_ITEM.setMode(RADIANS_DEGREES_MODE);
		SINE_GENERATOR_ITEM.setMode(RADIANS_DEGREES_MODE);
		ARC_COSINE_GENERATOR_ITEM.setMode(RADIANS_DEGREES_MODE);
		ARC_SINE_GENERATOR_ITEM.setMode(RADIANS_DEGREES_MODE);
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
		registry.add(ARC_COSINE_GENERATOR_ITEM);
		registry.add(SINE_GENERATOR_ITEM);
		registry.add(ARC_SINE_GENERATOR_ITEM);
		registry.add(PI);
		registry.add(SQRT_GENERATOR_ITEM);
	}

	private Defaults() {

	}
}

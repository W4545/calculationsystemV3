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
package parts.lost.calculationsystem.core.registry.defaults.generators;

// Name: Jack Young
// Date: 3/13/2020

import parts.lost.calculationsystem.core.Conversions;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class SineGeneratorItem extends RDGeneratorItem {

	public static final GenOperation SINE_GEN_OPERATION_RADIAN = values -> new Value(Math.sin(values[0].value().getDouble()));

	public static final GenOperation SINE_GEN_OPERATION_DEGREES = values -> new Value(Math.sin(Conversions.toRadians(values[0].value().getDouble())));

	public SineGeneratorItem() {
		super("sin", 1, SINE_GEN_OPERATION_RADIAN, SINE_GEN_OPERATION_DEGREES);
	}
}

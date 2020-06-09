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
// Date: 3/29/2020

import parts.lost.calculationsystem.core.registry.types.GeneratorTemplate;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class SqrtGeneratorTemplate extends GeneratorTemplate {

	public static final GenOperation SQRT_OPERATION = values -> new Value(Math.sqrt(values[0].value().getDouble()));

	public SqrtGeneratorTemplate() {
		super("sqrt", 1, SQRT_OPERATION);
	}
}

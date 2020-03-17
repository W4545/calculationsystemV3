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
package parts.lost.calcsystem.registry.defaults.generators;

// Name: Jack Young
// Date: 3/13/2020

import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.types.operations.GenOperation;
import parts.lost.calcsystem.types.operations.defaults.CosineGenOperation;

public class CosineGenerator extends GeneratorItem {
	public CosineGenerator() {
		super("cos", 1, CosineGenOperation.COSINE_GEN_OPERATION);
	}
}

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
package parts.lost.calculationsystem.variables;

import parts.lost.calculationsystem.core.Calculate;
import parts.lost.calculationsystem.variables.extensions.builder.VariableBuilderExtension;
import parts.lost.calculationsystem.variables.extensions.infix.VariableInfixExtension;
import parts.lost.calculationsystem.variables.extensions.yield.VariableYield;
import parts.lost.calculationsystem.variables.registry.types.VariableTemplate;

public class Variables {

    public static void quickSetup(Calculate calculate) {
        calculate.getBuilderExtensions().add(new VariableBuilderExtension());
        calculate.setYield(new VariableYield());
        calculate.getInfixExtensions().add(new VariableInfixExtension());
    }

    public static void addVariable(Calculate calculate, VariableTemplate template) {
        calculate.getParser().getRegistry().add(template);
    }
}

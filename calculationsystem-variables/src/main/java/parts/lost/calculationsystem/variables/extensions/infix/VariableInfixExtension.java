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
package parts.lost.calculationsystem.variables.extensions.infix;

import parts.lost.calculationsystem.core.Flag;
import parts.lost.calculationsystem.core.extensions.infix.InfixExtension;
import parts.lost.calculationsystem.variables.registry.types.VariableTemplate;

import java.util.Deque;
import java.util.List;

public class VariableInfixExtension implements InfixExtension {
    @Override
    public void action(Flag flag, List<Flag> output, Deque<Flag> stack) {
        output.add(flag);
    }

    @Override
    public boolean accept(Flag flag) {
        return flag.getObject() instanceof VariableTemplate;
    }
}

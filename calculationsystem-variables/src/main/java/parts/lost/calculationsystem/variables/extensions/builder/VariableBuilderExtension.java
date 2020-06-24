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
package parts.lost.calculationsystem.variables.extensions.builder;

import parts.lost.calculationsystem.core.Flag;
import parts.lost.calculationsystem.core.State;
import parts.lost.calculationsystem.core.extensions.builder.BuilderExtension;
import parts.lost.calculationsystem.core.types.TreeType;
import parts.lost.calculationsystem.variables.registry.types.VariableTemplate;
import parts.lost.calculationsystem.variables.types.Variable;

import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class VariableBuilderExtension extends BuilderExtension<VariableTemplate> {

    public VariableBuilderExtension() {
    }

    @Override
    public void action(VariableTemplate object, Deque<TreeType> stack, State state) {
        Set<Variable> variableSet = state.getStorage("VARIABLES");
        Variable toAdd = null;

        if (variableSet == null) {
            variableSet = new HashSet<>();
            state.setStorage("VARIABLES", variableSet);
        } else {
            for (Variable variable : variableSet) {
                if (variable.getIdentifier().equals(object.getIdentifier()))
                    toAdd = variable;
            }
        }

        if (toAdd == null) {
            toAdd = new Variable(object.getIdentifier(), object.getStartValue());
            variableSet.add(toAdd);
        }

        stack.push(toAdd);
    }

    @Override
    public boolean accept(Flag flag) {
        return flag.getObject() instanceof VariableTemplate;
    }
}

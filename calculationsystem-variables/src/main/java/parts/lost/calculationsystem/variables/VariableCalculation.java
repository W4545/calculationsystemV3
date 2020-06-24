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

import parts.lost.calculationsystem.core.Calculation;
import parts.lost.calculationsystem.core.types.TreeType;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.variables.types.Variable;

import java.util.Set;

public class VariableCalculation extends Calculation {

    private final Set<Variable> variables;
    private boolean cached = false;

    public VariableCalculation(String representation, TreeType tree, Set<Variable> variables) {
        super(representation, tree);
        this.variables = variables;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public void clearCache() {
        cached = false;
    }

    public boolean isCached() {
        return cached;
    }

    public void setVariable(String identifier, Value value) {
        if (variables != null) {
            cached = false;
            for (Variable variable : variables) {
                if (variable.getIdentifier().equals(identifier))
                    variable.setValue(value);
            }
        }
    }

    public void setVariable(String identifier, double value) {
        setVariable(identifier, new Value(value));
    }

    @Override
    public Value solve() {
        if (!cached) {
            value = tree.value();
            cached = true;
        }

        return value;
    }
}

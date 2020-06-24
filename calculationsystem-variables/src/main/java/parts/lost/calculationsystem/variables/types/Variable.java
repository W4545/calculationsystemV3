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
package parts.lost.calculationsystem.variables.types;

import parts.lost.calculationsystem.core.types.TreeType;
import parts.lost.calculationsystem.core.types.Value;

import java.util.Objects;

public class Variable implements TreeType {

    private String identifier;
    private Value value;

    public Variable(String identifier, Value start) {
        this.identifier = identifier;
        value = start;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = new Value(value);
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return identifier.equals(variable.identifier) &&
                value.equals(variable.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, value);
    }

    @Override
    public Value value() {
        return value;
    }
}

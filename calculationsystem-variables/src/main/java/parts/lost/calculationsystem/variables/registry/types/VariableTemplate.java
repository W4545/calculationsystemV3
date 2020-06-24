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
package parts.lost.calculationsystem.variables.registry.types;

import parts.lost.calculationsystem.core.Flag;
import parts.lost.calculationsystem.core.Priority;
import parts.lost.calculationsystem.core.registry.types.Template;
import parts.lost.calculationsystem.core.types.Value;

public class VariableTemplate extends Template {
    
    private final Value value;
    
    public VariableTemplate(String identifier) {
        super(identifier, Priority.ONE);
        this.value = new Value(0.0d);
    }
    
    public VariableTemplate(String identifier, Value start) {
        super(identifier, Priority.ONE);
        this.value = start;
    }
    
    public Value getStartValue() {
        return value;
    }

    @Override
    public Flag generateFlag() {
        return new Flag(this);
    }
}

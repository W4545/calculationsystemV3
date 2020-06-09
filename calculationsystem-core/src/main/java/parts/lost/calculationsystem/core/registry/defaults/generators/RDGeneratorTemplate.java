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

import parts.lost.calculationsystem.core.registry.Moddable;
import parts.lost.calculationsystem.core.registry.Mode;
import parts.lost.calculationsystem.core.registry.defaults.modes.enums.RD;
import parts.lost.calculationsystem.core.registry.types.GeneratorTemplate;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class RDGeneratorTemplate extends GeneratorTemplate implements Moddable<RD> {

    private GenOperation radians;
    private GenOperation degrees;

    private Mode<RD> mode = null;

    public RDGeneratorTemplate(String identifier, int argumentCount, GenOperation radians, GenOperation degrees) {
        super(identifier, argumentCount, radians);
        this.radians = radians;
        this.degrees = degrees;
    }

    @Override
    public void setMode(Mode<RD> mode) {
        this.mode = mode;
    }

    @Override
    public GenOperation getOperation() {
        return mode != null && mode.getCurrentState().equals(RD.DEGREES) ? degrees : radians;
    }
}

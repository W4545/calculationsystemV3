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

import parts.lost.calculationsystem.core.CalculationManager;
import parts.lost.calculationsystem.core.Flag;
import parts.lost.calculationsystem.core.State;
import parts.lost.calculationsystem.core.types.TreeType;

import java.util.List;

public class VariableManager extends CalculationManager {

    public VariableManager(State state) {
        super(state);
    }

    @Override
    public List<Flag> postParser(List<Flag> parsed) {
        return parsed;
    }

    @Override
    public Flag[] postFix(Flag[] postFix) {
        return postFix;
    }

    @Override
    public Flag[] postBuild(Flag[] postBuild) {
        return postBuild;
    }

    @Override
    public TreeType postBuild(TreeType tree) {
        return tree;
    }

    @Override
    public void cleanup() {

    }
}

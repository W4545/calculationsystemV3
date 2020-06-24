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
package parts.lost.calculationsystem.core;

import parts.lost.calculationsystem.core.types.TreeType;

import java.util.List;

public abstract class CalculationManager {

    protected State state;

    public CalculationManager(State state) {
        this.state = state;
    }

    public abstract List<Flag> postParser(List<Flag> parsed);

    public abstract Flag[] postFix(Flag[] postFix);

    public abstract Flag[] postBuild(Flag[] postBuild);

    public abstract TreeType postBuild(TreeType tree);

    public abstract void cleanup();
}

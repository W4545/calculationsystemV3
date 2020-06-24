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
package parts.lost.calculationsystem.core.extensions.builder.defaults;

import parts.lost.calculationsystem.core.Flag;
import parts.lost.calculationsystem.core.State;
import parts.lost.calculationsystem.core.extensions.builder.BuilderExtension;
import parts.lost.calculationsystem.core.registry.types.UnaryTemplate;
import parts.lost.calculationsystem.core.types.TreeType;
import parts.lost.calculationsystem.core.types.UnaryOperator;

import java.util.Deque;

public class UnaryBuilderExtension extends BuilderExtension<UnaryTemplate> {

    @Override
    public void action(UnaryTemplate object, Deque<TreeType> stack, State state) {
        stack.push(new UnaryOperator(stack.pop(), object.getOperation()));
    }

    @Override
    public boolean accept(Flag flag) {
        return flag.isUnaryOperator();
    }
}

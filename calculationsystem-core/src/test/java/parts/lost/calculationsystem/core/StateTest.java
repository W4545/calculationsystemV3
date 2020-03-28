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

import org.junit.Test;

import static org.junit.Assert.*;

public class StateTest {

    @Test
    public void getExpression() {
        String expression = "45*5-3";
        State state = new State(expression);
        assertSame(expression, state.getExpression());
        assertNotSame("5+5", state.getExpression());
    }

    @Test
    public void setExpression() {
        String initial = "55+5*69";
        String adjusted = "5+5";

        State state = new State(initial);
        assertNotSame(adjusted, state.getExpression());
        state.setExpression(adjusted);
        assertSame(adjusted, state.getExpression());
    }
}
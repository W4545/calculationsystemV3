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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import parts.lost.calculationsystem.core.registry.Defaults;
import parts.lost.calculationsystem.core.types.Generator;
import parts.lost.calculationsystem.core.types.TreeType;
import parts.lost.calculationsystem.core.types.Value;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FlagTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Value value = new Value(443.3);
        String open = "(";
        String closed = ")";
        String comma = ",";
        Generator generator = new Generator(new TreeType[0], Defaults.COSINE_GENERATOR.getOperation());

        return Arrays.asList(new Object[][] {
                {new Flag(value), value, 1},
                {new Flag(Defaults.ADDITION), Defaults.ADDITION, 2},
                {new Flag(Defaults.COSINE_GENERATOR), Defaults.COSINE_GENERATOR, 3},
                {new Flag(open), open, 4},
                {new Flag(closed), closed, 5},
                {new Flag(comma), comma, 6},
                {new Flag(generator), generator, 7},
                {new Flag(Defaults.PI), Defaults.PI, 8},
                {new Flag(Defaults.NEGATION), Defaults.NEGATION, 9}
        });
    }

    private Flag flag;
    private Object expectedObject;
    private int method;

    public FlagTest(Flag flag, Object expectedObject, int method) {
        this.flag = flag;
        this.expectedObject = expectedObject;
        this.method = method;
    }

    private void booleanTest(int methodNumber, boolean value) {
        if (method == methodNumber)
            assertTrue(value);
        else
            assertFalse(value);
    }

    @Test
    public void isNumber() {
        booleanTest(1, flag.isNumber());
    }

    @Test
    public void isOperator() {
        booleanTest(2, flag.isOperator());
    }

    @Test
    public void isGeneratorItem() {
        booleanTest(3, flag.isGeneratorItem());
    }

    @Test
    public void isOpenParentheses() {
        booleanTest(4, flag.isOpenParentheses());
    }

    @Test
    public void isCloseParentheses() {
        booleanTest(5, flag.isCloseParentheses());
    }

    @Test
    public void isComma() {
        booleanTest(6, flag.isComma());
    }

    @Test
    public void isGenerator() {
        booleanTest(7, flag.isGenerator());
    }

    @Test
    public void isConstant() {
        booleanTest(8, flag.isConstant());
    }

    @Test
    public void isUnaryOperator() {
        booleanTest(9, flag.isUnaryOperator());
    }

    @Test
    public void getObject() {
        assertSame(expectedObject, flag.getObject());
    }
}
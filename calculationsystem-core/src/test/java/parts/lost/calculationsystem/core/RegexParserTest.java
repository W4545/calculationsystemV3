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
import parts.lost.calculationsystem.core.registry.Registry;

import static org.junit.Assert.*;

public class RegexParserTest {

    @Test
    public void assumeParentheses() {
        RegexParser regexParser = new RegexParser(false);

        assertFalse(regexParser.assumeParentheses());

        RegexParser regexParser1 = new RegexParser(true);

        assertTrue(regexParser1.assumeParentheses());
    }

    @Test
    public void testAssumeParentheses() {
        RegexParser regexParser = new RegexParser();
        regexParser.assumeParentheses(true);
        assertTrue(regexParser.assumeParentheses());
        regexParser.assumeParentheses(false);
        assertFalse(regexParser.assumeParentheses());
    }

    @Test
    public void getRegistry() {
        Registry registry = new Registry();

        RegexParser regexParser = new RegexParser(registry);

        assertSame(registry, regexParser.getRegistry());
    }

    @Test
    public void parenthesesCount() {
        RegexParser regexParser = new RegexParser();
        assertArrayEquals(new int[] {4, 4}, regexParser.parenthesesCount("lalala33243(((234324432)))()"));
        assertArrayEquals(new int[] {0, 0}, regexParser.parenthesesCount("asdomd siodasdim siom324234 32434234"));
        assertArrayEquals(new int[] {5, 5}, regexParser.parenthesesCount("32+3*(44+(4)-44/(4-3)+(5+4)-(4/4))"));
        assertArrayEquals(new int[] {0, 1}, regexParser.parenthesesCount("44+533)5435"));
    }

    @Test
    public void parse() {
    }

    @Test
    public void parseGeneratorOperator() {
    }
}
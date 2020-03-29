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

public class ConversionsTest {
    public static final double DELTA = .0000005d;

    @Test
    public void toRadians() {
        assertEquals(Math.PI, Conversions.toRadians(180d), DELTA);
        assertEquals(Math.PI / 180, Conversions.toRadians(1d), DELTA);
    }

    @Test
    public void toDegrees() {
        assertEquals(180d, Conversions.toDegrees(Math.PI), DELTA);
        assertEquals(1d, Conversions.toDegrees(Math.PI / 180d), DELTA);
    }
}
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
package parts.lost.calculationsystem.core.types;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ValueTest {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{new Value(55), 55.0d},
				{new Value(33.56983d), 33.56983d},
				{new Value(Double.MIN_VALUE), Double.MIN_VALUE},
				{new Value(), 0.0d},
				{new Value(-55.443d), -55.443d}
		});
	}

	private Value value;
	private double expected;

	public ValueTest(Value value, double expected) {
		this.value = value;
		this.expected = expected;
	}

	@Test
	public void value() {
		assertEquals(value, value.value());
	}

	@Test
	public void getDouble() {
		assertEquals(expected, value.getDouble(), 0.0000001d);
	}

	@Test
	public void negate() {
		assertEquals(-expected, new Value(value.getDouble()).negate().getDouble(), 0.00000001d);
	}

	@Test
	public void testToString() {
		assertEquals(Double.toString(expected), value.toString());
	}

	@Test
	public void testEquals() {
		assertEquals(value, value);
		assertNotEquals(new Value(value.getDouble() + .00000003d), value);
	}

	@Test
	public void testHashCode() {
		assertEquals(new Value(value.getDouble()).hashCode(), value.hashCode());
	}
}
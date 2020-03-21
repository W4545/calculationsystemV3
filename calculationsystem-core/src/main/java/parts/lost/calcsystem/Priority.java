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
package parts.lost.calcsystem;

/**
 * Represents the prioritization of the various operators, generators, and numbers.
 * Higher numbers are higher priority.
 * @version 0.5.0
 * @since 0.0
 */
public enum Priority {
	/**
	 * Reserved for ordinary numbers and generators
	 */
	ONE,
	TWO,
	/**
	 * Reserved for operators that execute on the same level as addition and subtraction
	 */
	THREE,
	FOUR,

	/**
	 * Reserved for operators that execute on the same level as multiplication and subtraction
	 */
	FIVE,
	SIX,
	SEVEN,
	EIGHT,

	/**
	 * Reserved for unary operators that execute on the same level as negation
	 */
	NINE,
	TEN
}

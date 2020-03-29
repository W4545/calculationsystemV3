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

import parts.lost.calculationsystem.core.exceptions.CalculationExpressionFormatException;
import parts.lost.calculationsystem.core.exceptions.CalculationStringParsingException;
import parts.lost.calculationsystem.core.registry.Registry;
import parts.lost.calculationsystem.core.registry.types.Item;
import parts.lost.calculationsystem.core.registry.types.UnaryItem;
import parts.lost.calculationsystem.core.types.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegexParser implements Parser {

	// private String oldreg = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([a-zA-Z]+)|([*+\\-/%^])|([(])|([)])";
	// private String oldreg2 = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([*+\\-/%^(),])|([^\\d()*+\\-/%^,]+)";

	private Pattern pattern;

	public RegexParser() {
		pattern = Pattern.compile("(\\d+(?:[.]\\d*)?|[.]\\d+)|([*+\\-/%^])|([(),])|([^\\d()*+\\-/%^,.\\s]+)");

	}

	public List<Flag> parse(String string, Registry registry, State state) {
		var matcher = pattern.matcher(string);

		List<Flag> groups = new ArrayList<>();
		int pos = 0;
		try {
			while (matcher.find()) {
				String output1 = matcher.group(1);
				String output2 = matcher.group(2);
				String output3 = matcher.group(3);
				String output4 = matcher.group(4);

				if (output1 != null) {
					// Matched a number
					groups.add(new Flag(new Value(Double.parseDouble(output1))));
				} else if (output2 != null) {
					// Matched an operator
					parseGeneratorOperator(registry, state, groups, pos, output2);
				} else if (output3 != null) // Found comma or parentheses
					groups.add(new Flag(output3));
				else if (output4 != null) {
					// Found generator/operator
					parseGeneratorOperator(registry, state, groups, pos, output4);
				}
				pos += 1;
			}
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			throw new CalculationExpressionFormatException(state, ex);
		} catch (StackOverflowError ex) {
			throw new CalculationExpressionFormatException(state);
		}

		return groups;
	}

	protected void parseGeneratorOperator(Registry registry, State state, List<Flag> groups, int pos, String output) {
		if (pos > 0) {
			Flag previous = groups.get(pos - 1);
			if (previous.isOpenParentheses() || previous.isComma() || previous.isOperator() || previous.isUnaryOperator()) {
				for (UnaryItem item : registry.getUnaryOperators()) {
					if (item.getIdentifier().equals(output)) {
						groups.add(new Flag(item));
						return;
					}
				}
			}
		} else if (pos == 0) {
			for (UnaryItem item : registry.getUnaryOperators()) {
				if (item.getIdentifier().equals(output)) {
					groups.add(new Flag(item));
					return;
				}
			}
		}
		for (Item item : registry) {
			if (item.getIdentifier().equals(output)) {
				groups.add(new Flag(item));
				return;
			}
		}
		throw new CalculationStringParsingException(state, output);
	}
}

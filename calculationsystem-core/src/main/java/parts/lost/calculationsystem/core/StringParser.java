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

import parts.lost.calculationsystem.core.registry.Registry;
import parts.lost.calculationsystem.core.registry.types.Item;
import parts.lost.calculationsystem.core.registry.types.UnaryItem;
import parts.lost.calculationsystem.core.types.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringParser {

	// private String oldreg = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([a-zA-Z]+)|([*+\\-/%^])|([(])|([)])";
	// private String oldreg2 = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([*+\\-/%^(),])|([^\\d()*+\\-/%^,]+)";

	private Pattern pattern;

	public StringParser() {
		pattern = Pattern.compile("(\\d+(?:[.]\\d*)?|[.]\\d+)|([*+\\-/%^])|([(),])|([^\\d()*+\\-/%^,.\\s]+)");

	}

	protected List<Flag> parseStringRegex(String string, Registry registry) {
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
					parseGeneratorOperator(registry, groups, pos, output2);
				} else if (output3 != null) // Found comma or parentheses
					groups.add(new Flag(output3));
				else if (output4 != null) {
					// Found generator/operator
					parseGeneratorOperator(registry, groups, pos, output4);
				}
				pos += 1;
			}
		} catch (StackOverflowError | NumberFormatException ex) {
			throw new RuntimeException("Incorrect syntax in string.", ex);
		}

		return groups;
	}

	private void parseGeneratorOperator(Registry registry, List<Flag> groups, int pos, String output4) {
		if (pos > 0) {
			Flag previous = groups.get(pos - 1);
			if (previous.isOpenParentheses() || previous.isComma() || previous.isOperator() || previous.isUnaryOperator()) {
				for (UnaryItem item : registry.getUnaryOperators()) {
					if (item.getIdentifier().equals(output4)) {
						groups.add(new Flag(item));
						return;
					}
				}
			}
		} else if (pos == 0) {
			for (UnaryItem item : registry.getUnaryOperators()) {
				if (item.getIdentifier().equals(output4)) {
					groups.add(new Flag(item));
					return;
				}
			}
		}
		for (Item item : registry) {
			if (item.getIdentifier().equals(output4)) {
				groups.add(new Flag(item));
				return;
			}
		}
	}
}

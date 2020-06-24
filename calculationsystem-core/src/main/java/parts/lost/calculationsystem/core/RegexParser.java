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
import parts.lost.calculationsystem.core.registry.types.Template;
import parts.lost.calculationsystem.core.registry.types.UnaryTemplate;
import parts.lost.calculationsystem.core.types.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegexParser implements Parser {

	// private String oldreg = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([a-zA-Z]+)|([*+\\-/%^])|([(])|([)])";
	// private String oldreg2 = "(\\d+(?:[.]\\d*)?)|([.]\\d+)|([*+\\-/%^(),])|([^\\d()*+\\-/%^,]+)";

	private Pattern pattern;
	private Registry registry;
	private boolean assumeParentheses;

	public RegexParser() {
		pattern = Pattern.compile("(\\d+(?:[.]\\d*)?|[.]\\d+)|([*+\\-/%^])|([(),])|([^\\d()*+\\-/%^,.\\s]+)");
		registry = new Registry();
		assumeParentheses = true;
	}

	public RegexParser(Registry registry) {
		this.registry = registry;
	}

	public RegexParser(boolean assumeParentheses) {
		pattern = Pattern.compile("(\\d+(?:[.]\\d*)?|[.]\\d+)|([*+\\-/%^])|([(),])|([^\\d()*+\\-/%^,.\\s]+)");
		this.assumeParentheses = assumeParentheses;
	}

	public RegexParser(Registry registry, boolean assumeParentheses) {
		this.registry = registry;
		this.assumeParentheses = assumeParentheses;
	}

	public boolean assumeParentheses() {
		return assumeParentheses;
	}

	public void assumeParentheses(boolean assumeParentheses) {
		this.assumeParentheses = assumeParentheses;
	}

	@Override
	public Registry getRegistry() {
		return registry;
	}

	/**
	 * Returns the count of opening and closing parentheses in that order
	 * @param expression The String expression
	 * @return An array with two integers
	 */
	protected int[] parenthesesCount(String expression) {
		int[] parenthesesCount = new int[2];

		for (int i = 0; i < expression.length(); i += 1) {
			char character = expression.charAt(i);
			if (character == '(')
				parenthesesCount[0] += 1;
			else if (character == ')')
				parenthesesCount[1] += 1;
		}

		return parenthesesCount;
	}

	public List<Flag> parse(State state) {
		if (assumeParentheses) {
			int[] parenthesesCount = parenthesesCount(state.getExpression());

			if (parenthesesCount[0] < parenthesesCount[1])
				throw new CalculationStringParsingException(state, "Cannot assume opening parentheses");
			else if (parenthesesCount[0] > parenthesesCount[1])
				state.setExpression(state.getExpression().concat(")".repeat(parenthesesCount[0] - parenthesesCount[1])));
		}

		var matcher = pattern.matcher(state.getExpression());

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
				for (UnaryTemplate item : registry.getUnaryOperators()) {
					if (item.matches(output)) {
						groups.add(item.generateFlag());
						return;
					}
				}
			}
		} else if (pos == 0) {
			for (UnaryTemplate item : registry.getUnaryOperators()) {
				if (item.matches(output)) {
					groups.add(item.generateFlag());
					return;
				}
			}
		}
		for (Template template : registry) {
			if (template.matches(output)) {
				groups.add(template.generateFlag());
				return;
			}
		}
		throw new CalculationStringParsingException(state, output);
	}
}

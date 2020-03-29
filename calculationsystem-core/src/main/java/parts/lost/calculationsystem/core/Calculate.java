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
import parts.lost.calculationsystem.core.exceptions.CalculationGeneratorFormattingException;
import parts.lost.calculationsystem.core.registry.Registry;
import parts.lost.calculationsystem.core.registry.types.ConstantItem;
import parts.lost.calculationsystem.core.registry.types.GeneratorItem;
import parts.lost.calculationsystem.core.registry.types.OperatorItem;
import parts.lost.calculationsystem.core.registry.types.UnaryItem;
import parts.lost.calculationsystem.core.types.Generator;
import parts.lost.calculationsystem.core.types.Operator;
import parts.lost.calculationsystem.core.types.TreeType;
import parts.lost.calculationsystem.core.types.UnaryOperator;

import java.util.*;

public class Calculate {

	private Registry registry;
	private Parser parser;
	private State state = null;


	public Calculate() {
		registry = new Registry();
		parser = new RegexParser();
	}

	public Calculate(Registry registry) {
		this.registry = registry;
		parser = new RegexParser();
	}

	public Calculate(Parser parser) {
		this.parser = parser;
		registry = new Registry();
	}

	public Calculate(Registry registry, Parser parser) {
		this.registry = registry;
		this.parser = parser;
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	protected void cleanUpAndThrow(RuntimeException ex) throws RuntimeException {
		state = null;
		throw ex;
	}

	protected List<TreeType> buildGenerator(List<Flag> list) {
		List<TreeType> toOutput = new ArrayList<>();
		List<Flag> build = new ArrayList<>();
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).isGeneratorItem()) {
				int[] pos = outerParenthesis(list, i + 1);
				List<TreeType> genTrees = buildGenerator(list.subList(pos[0], pos[1]));
				genSetup(list, build, i, genTrees);
				i = pos[1];
			} else if (list.get(i).isComma()) {
				Flag[] postfix = infixToPostfix(build.toArray(new Flag[0]));
				toOutput.add(buildTree(postfix));
				build.clear();
			} else {
				build.add(list.get(i));
			}

		}
		if (build.size() > 0)
		{
			Flag[] postfix = infixToPostfix(build.toArray(new Flag[0]));
			toOutput.add(buildTree(postfix));
		}

		return toOutput;
	}

	/**
	 *
	 * @param list list of complete expression (may be a partial expression, but must contain the complete generator
	 * @param build list being used to build the new expression with the properly set up Generator object
	 * @param i Position of GeneratorItem Flag in list
	 * @param genTrees An array of the expressions inside the generator (expressions are represented as {@link TreeType})
	 */
	private void genSetup(List<Flag> list, List<Flag> build, int i, List<TreeType> genTrees) {
		GeneratorItem item = (GeneratorItem) list.get(i).getObject();
		if (item.getArgumentCount() != -1 && genTrees.size() != item.getArgumentCount())
			cleanUpAndThrow(new CalculationGeneratorFormattingException(String.format("Incorrect number of arguments passed to generator \"%s\"", item.getIdentifier())));
		build.add(new Flag(new Generator(genTrees.toArray(new TreeType[0]), item.getOperation())));
	}

	protected int[] outerParenthesis(List<Flag> flags, int start) {
		int openParenthesisPos = -1;
		int closedParenthesisPos = -1;
		if (start + 1 != flags.size() && flags.get(start).isOpenParentheses())
			openParenthesisPos = start;
		else
			cleanUpAndThrow(new CalculationGeneratorFormattingException("missing an opening \"(\""));
		int openParenthesesEncountered = 0;
		for (int k = start + 1; k < flags.size(); k++) {
			if (flags.get(k).isOpenParentheses())
				openParenthesesEncountered++;
			else if (flags.get(k).isCloseParentheses() && openParenthesesEncountered > 0)
				openParenthesesEncountered--;
			else if (flags.get(k).isCloseParentheses() && openParenthesesEncountered == 0) {
				closedParenthesisPos = k;
				break;
			}
		}

		if (closedParenthesisPos == -1)
			cleanUpAndThrow(new CalculationGeneratorFormattingException("missing an opening \")\""));

		return new int[] {openParenthesisPos, closedParenthesisPos};
	}

	protected Flag[] generatorParse(List<Flag> flags) {
		List<Flag> list = new ArrayList<>();
		try {
			for (int i = 0; i < flags.size(); i++) {
				if (flags.get(i).isGeneratorItem()) {
					int[] pos = outerParenthesis(flags, i + 1);
					List<TreeType> genTrees = buildGenerator(flags.subList(pos[0] + 1, pos[1]));
					genSetup(flags, list, i, genTrees);
					i = pos[1];
				}
				else
					list.add(flags.get(i));

			}


		} catch (IndexOutOfBoundsException ex) {
			cleanUpAndThrow(new CalculationExpressionFormatException(state, ex));
		}

		return list.toArray(new Flag[0]);
	}

	protected Flag[] infixToPostfix(Flag[] flags) {
		Flag[] output = new Flag[flags.length];

		ArrayDeque<Flag> stack = new ArrayDeque<>();
		int pos = 0;
		for (Flag flag : flags) {
			if (flag.isNumber() || flag.isGenerator() || flag.isConstant())
				output[pos++] = flag;
			else if (flag.isOperator()) {
				while (!stack.isEmpty() && ((stack.peek().isOperator() &&
						((OperatorItem) Objects.requireNonNull(stack.peek()).getObject()).getPriority().compareTo(((OperatorItem) flag.getObject()).getPriority()) >= 0) || Objects.requireNonNull(stack.peek()).isUnaryOperator())) {
					output[pos++] = stack.pop();
				}
				stack.push(flag);
			} else if (flag.isUnaryOperator()) {
				while (!stack.isEmpty() && !stack.peek().isOperator()) {
					output[pos++] = stack.pop();
				}
				stack.push(flag);
			} else if (flag.isOpenParentheses()) {
				stack.push(flag);
			} else if (!stack.isEmpty() && flag.isCloseParentheses()) {
				while (!stack.isEmpty() && !stack.peek().isOpenParentheses())
					output[pos++] = stack.pop();
				stack.pop();
			} else {
				cleanUpAndThrow(new CalculationExpressionFormatException(state));
			}
		}
		while (!stack.isEmpty())
			output[pos++] = stack.pop();

		return output;
	}

	protected TreeType buildTree(Flag[] postFix) {
		ArrayDeque<TreeType> stack = new ArrayDeque<>();
		try {
			for (Flag flag : postFix) {
				if (flag.isNumber() || flag.isGenerator()) {
					stack.push((TreeType) flag.getObject());
				} else if (flag.isConstant()) {
					stack.push(((ConstantItem) flag.getObject()).getValue());
				} else if (flag.isOperator()) {
					TreeType right = stack.pop();
					TreeType left = stack.pop();
					stack.push(new Operator(left, right, ((OperatorItem) flag.getObject()).getOperation()));
				} else if (flag.isUnaryOperator()) {
					TreeType one = stack.pop();
					stack.push(new UnaryOperator(one, ((UnaryItem)flag.getObject()).getOperation()));
				}
			}

		} catch (NoSuchElementException | NullPointerException ex) {
			cleanUpAndThrow(new CalculationExpressionFormatException(state, ex));
		}
		if (stack.size() != 1)
			cleanUpAndThrow(new CalculationExpressionFormatException(state));
		return stack.pop();
	}

	public Calculation interpolate(String string) {
		state = new State(string);
		List<Flag> groups = parser.parse(string, registry, state);

		Flag[] generatorPass = generatorParse(groups);
		Flag[] postFix = infixToPostfix(generatorPass);


		state = null;
		return new Calculation(string, buildTree(postFix));
	}

	public double calculate(String string) {
		return interpolate(string).solve().getDouble();
	}
}

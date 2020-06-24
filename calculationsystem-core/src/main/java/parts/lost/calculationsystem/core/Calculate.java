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
import parts.lost.calculationsystem.core.extensions.builder.BuilderExtension;
import parts.lost.calculationsystem.core.extensions.builder.defaults.*;
import parts.lost.calculationsystem.core.extensions.infix.InfixExtension;
import parts.lost.calculationsystem.core.extensions.infix.defaults.*;
import parts.lost.calculationsystem.core.extensions.yield.Yield;
import parts.lost.calculationsystem.core.extensions.yield.defaults.StandardYield;
import parts.lost.calculationsystem.core.registry.Registry;
import parts.lost.calculationsystem.core.registry.types.GeneratorTemplate;
import parts.lost.calculationsystem.core.registry.types.OperatorTemplate;
import parts.lost.calculationsystem.core.types.Generator;
import parts.lost.calculationsystem.core.types.TreeType;

import java.util.*;

public class Calculate {

	private Parser parser;
	private Yield<? extends Calculation> yield = new StandardYield();
	private final Set<BuilderExtension<?>> builderExtensions = new HashSet<>();
	private final Set<InfixExtension> infixExtensions = new HashSet<>();
	private final Set<CalculationManagerFactory> calculationManagerFactories = new HashSet<>();
	private State state = null;


	public Calculate() {
		parser = new RegexParser();
		defaults();
	}

	public Calculate(Parser parser) {
		this.parser = parser;
		defaults();
	}

	public Calculate(Registry registry) {
		parser = new RegexParser(registry);
		defaults();
	}

	public Calculate(boolean defaults) {
		if (defaults)
			defaults();
		parser = new RegexParser();
	}

	protected void defaults() {
		builderExtensions.add(new ConstantBuilderExtension());
		builderExtensions.add(new GeneratorBuilderExtension());
		builderExtensions.add(new OperatorBuilderExtension());
		builderExtensions.add(new UnaryBuilderExtension());
		builderExtensions.add(new ValueBuilderExtension());

		infixExtensions.add(new ClosedParenthesesInfixExtension());
		infixExtensions.add(new ConstantInfixExtension());
		infixExtensions.add(new GeneratorInfixExtension());
		infixExtensions.add(new NumberInfixExtension());
		infixExtensions.add(new OpenParenthesesInfixExtension());
		infixExtensions.add(new OperatorInfixExtension());
		infixExtensions.add(new UnaryOperatorInfixExtension());
	}

	public Registry getRegistry() {
		return parser.getRegistry();
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public void setYield(Yield<? extends Calculation> yield) {
		this.yield = yield;
	}

	public Set<BuilderExtension<?>> getBuilderExtensions() {
		return builderExtensions;
	}

	public Set<CalculationManagerFactory> getCalculationManagerFactories() {
		return calculationManagerFactories;
	}

	public Set<InfixExtension> getInfixExtensions() {
		return infixExtensions;
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
	protected void genSetup(List<Flag> list, List<Flag> build, int i, List<TreeType> genTrees) {
		GeneratorTemplate item = list.get(i).getObject();
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
		int i = 0;
		for (Flag flag : flags) {
			if (flag.isOpenParentheses())
				i += 1;
			else if (flag.isCloseParentheses())
				i += 1;
		}

		List<Flag> output = new ArrayList<>(flags.length - i);

		ArrayDeque<Flag> stack = new ArrayDeque<>();

		Outer:
		for (Flag flag : flags) {
			for (InfixExtension extension : infixExtensions) {
				if (extension.accept(flag)) {
					extension.action(flag, output, stack);
					continue Outer;
				}
			}
			cleanUpAndThrow(new CalculationExpressionFormatException(state));
		}
		while (!stack.isEmpty())
			output.add(stack.pop());

		return output.toArray(new Flag[0]);
	}

	protected TreeType buildTree(Flag[] postFix) {
		ArrayDeque<TreeType> stack = new ArrayDeque<>();
		try {
			for (Flag flag : postFix) {
				for (BuilderExtension<?> extension : builderExtensions) {
					if (extension.accept(flag)) {
						extension.action(flag.getObject(), stack, state);
						break;
					}
				}
			}

		} catch (NoSuchElementException | NullPointerException ex) {
			cleanUpAndThrow(new CalculationExpressionFormatException(state, ex));
		}
		if (stack.size() != 1)
			cleanUpAndThrow(new CalculationExpressionFormatException(state));
		return stack.pop();
	}

	public <T extends Calculation> T interpolate(String string) {
		return interpolate(new State(string));
	}

	@SuppressWarnings("unchecked")
	public <T extends Calculation> T interpolate(State state) {
		this.state = state;
		List<Flag> groups = parser.parse(state);

		Flag[] generatorPass = generatorParse(groups);
		Flag[] postFix = infixToPostfix(generatorPass);

		TreeType tree = buildTree(postFix);
		state.setTreeType(tree);

		this.state = null;

		return (T) yield.yield(state);
	}

	public double calculate(String string) {
		return interpolate(string).solve().getDouble();
	}

	public double calculate(State state) {
		return interpolate(state).solve().getDouble();
	}
}

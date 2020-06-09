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
package parts.lost.calculationsystem.core.registry;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calculationsystem.core.registry.types.*;

import java.util.*;
import java.util.function.Consumer;

/**
 * @version 0.5.2
 */
public class Registry implements Iterable<Template> {

	private Set<OperatorTemplate> operators;
	private Set<GeneratorTemplate> generators;
	private Set<ConstantTemplate> constants;
	private Set<UnaryTemplate> unaryOperators;

	public Registry() {
		init();
		loadDefaults();
	}

	public Registry(boolean loadDefaults) {
		init();
		if (loadDefaults)
			loadDefaults();
	}

	private void init() {
		operators = new HashSet<>(8);
		generators = new HashSet<>(8);
		constants = new HashSet<>(4);
		unaryOperators = new HashSet<>(2);
	}

	public void loadDefaults() {
		Defaults.load_defaults(this);
	}

	public boolean add(OperatorTemplate item) {
		return operators.add(item);
	}

	public boolean add(GeneratorTemplate item) {
		return generators.add(item);
	}

	public boolean add(UnaryTemplate item) {
		return unaryOperators.add(item);
	}

	public boolean add(ConstantTemplate item) {
		return constants.add(item);
	}

	public Set<OperatorTemplate> getOperators() {
		return operators;
	}

	public Set<GeneratorTemplate> getGenerators() {
		return generators;
	}

	public Set<ConstantTemplate> getConstants() {
		return constants;
	}

	public Set<UnaryTemplate> getUnaryOperators() {
		return unaryOperators;
	}

	public boolean contains(Template template) {
		return operators.contains(template) || generators.contains(template) ||
				constants.contains(template) || unaryOperators.contains(template);
	}

	@Override
	public Iterator<Template> iterator() {
		return new Iterator<>() {
			@SuppressWarnings("unchecked")
			private Iterator<? extends Template>[] iterators = new Iterator[4];
			{
				iterators[0] = operators.iterator();
				iterators[1] = generators.iterator();
				iterators[2] = constants.iterator();
				iterators[3] = unaryOperators.iterator();
			}

			private int current = 0;

			@Override
			public boolean hasNext() {
				for (int i = current; i < iterators.length; ++i) {
					if (iterators[i].hasNext()) {
						current = i;
						return true;
					}
				}
				return false;
			}

			@Override
			public Template next() {
				return iterators[current].next();
			}
		};
	}

	@Override
	public void forEach(Consumer<? super Template> action) {
		for (OperatorTemplate item : operators)
			action.accept(item);
		for (GeneratorTemplate item : generators)
			action.accept(item);
		for (ConstantTemplate item : constants)
			action.accept(item);
		for (UnaryTemplate item : unaryOperators)
			action.accept(item);
	}

	@Override
	public Spliterator<Template> spliterator() {
		return null;
	}
}

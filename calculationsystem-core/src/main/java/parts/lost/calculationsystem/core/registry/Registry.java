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

import parts.lost.calculationsystem.core.registry.defaults.generators.TestGenerator;
import parts.lost.calculationsystem.core.registry.types.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;

/**
 * @version 0.5.2
 */
public class Registry implements Iterable<Item> {

	private Set<OperatorItem> operators;
	private Set<GeneratorItem> generators;
	private Set<ConstantItem> constants;
	private Set<UnaryItem> unaryOperators;
	private Set<Mode<?>> modes;

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
		modes = new HashSet<>();
	}

	public void loadDefaults() {
		add(Defaults.ADDITION);
		add(Defaults.DIVISION);
		add(Defaults.MULTIPLICATION);
		add(Defaults.SUBTRACTION);
		add(Defaults.EXPONENT);
		add(Defaults.NEGATION);
		add(Defaults.POSITIVE);
		add(new TestGenerator());
		add(Defaults.SINE_GENERATOR);
		add(Defaults.PI);
	}

	@SuppressWarnings("unchecked")
	protected void modeLookup(Item item) {
		if (modes.size() > 0 && item instanceof Moddable) {
			Type[] generics = item.getClass().getGenericInterfaces();
			if (generics.length == 0) {
				Class<?> aClass = item.getClass().getSuperclass();
				while (generics.length == 0) {
					if (aClass == null)
						throw new RuntimeException();
					generics = aClass.getGenericInterfaces();
					aClass = aClass.getSuperclass();
				}

			}

			Class<?> modeClass = null;
			Class<?> typeClass = null;
			for (Type type : generics) {
				if (type instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) type;
					if (parameterizedType.getRawType().equals(Moddable.class)) {
						modeClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
						typeClass = (Class<?>) parameterizedType.getActualTypeArguments()[1];
						break;
					}
				}
			}

			for (Mode<?> mode : modes) {
				Type currentModeClass = (mode.getClass().getGenericSuperclass());
				if (mode != null && mode.getClass().equals(modeClass) && typeClass != null) {
					((Moddable) item).setMode(mode);
				}
			}
		}
	}

	public boolean add(OperatorItem item) {
		boolean status = operators.add(item);
		if (status)
			modeLookup(item);
		return status;
	}

	public boolean add(GeneratorItem item) {
		boolean status = generators.add(item);
		if (status)
			modeLookup(item);
		return status;
	}

	public boolean add(UnaryItem item) {
		boolean status = unaryOperators.add(item);
		if (status)
			modeLookup(item);
		return status;
	}

	public boolean add(ConstantItem item) {
		boolean status = constants.add(item);
		if (status)
			modeLookup(item);
		return status;
	}

	public boolean add(Mode<?> mode) {
		Class<?> modeClass = mode.getClass();
		for (Mode<?> aMode : modes) {
			if (aMode.getClass().equals(modeClass))
				throw new RuntimeException();
		}
		boolean status = modes.add(mode);
		if (status) {
			for (GeneratorItem item : generators)
				modeLookup(item);
			for (OperatorItem item : operators)
				modeLookup(item);
			for (UnaryItem item : unaryOperators)
				modeLookup(item);
			for (ConstantItem item : constants)
				modeLookup(item);
		}

		return status;
	}

	public Set<OperatorItem> getOperators() {
		return operators;
	}

	public Set<GeneratorItem> getGenerators() {
		return generators;
	}

	public Set<ConstantItem> getConstants() {
		return constants;
	}

	public Set<UnaryItem> getUnaryOperators() {
		return unaryOperators;
	}

	public boolean contains(Item item) {
		return operators.contains(item) || generators.contains(item) ||
				constants.contains(item) || unaryOperators.contains(item);
	}

	@Override
	public Iterator<Item> iterator() {
		return new Iterator<>() {
			@SuppressWarnings("unchecked")
			private Iterator<? extends Item>[] iterators = new Iterator[4];
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
			public Item next() {
				return iterators[current].next();
			}
		};
	}

	@Override
	public void forEach(Consumer<? super Item> action) {
		for (OperatorItem item : operators)
			action.accept(item);
		for (GeneratorItem item : generators)
			action.accept(item);
		for (ConstantItem item : constants)
			action.accept(item);
		for (UnaryItem item : unaryOperators)
			action.accept(item);
	}

	@Override
	public Spliterator<Item> spliterator() {
		return null;
	}
}

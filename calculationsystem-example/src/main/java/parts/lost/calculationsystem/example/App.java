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
package parts.lost.calculationsystem.example;

// Name: Jack Young
// Date: 3/21/2020

import parts.lost.calculationsystem.core.Calculate;
import parts.lost.calculationsystem.core.registry.Defaults;
import parts.lost.calculationsystem.core.registry.Moddable;
import parts.lost.calculationsystem.core.registry.Mode;
import parts.lost.calculationsystem.core.registry.defaults.modes.enums.RD;
import parts.lost.calculationsystem.core.registry.types.ConstantTemplate;
import parts.lost.calculationsystem.core.registry.types.GeneratorTemplate;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.core.types.operations.GenOperation;
import parts.lost.calculationsystem.variables.VariableCalculation;
import parts.lost.calculationsystem.variables.Variables;
import parts.lost.calculationsystem.variables.extensions.builder.VariableBuilderExtension;
import parts.lost.calculationsystem.variables.extensions.infix.VariableInfixExtension;
import parts.lost.calculationsystem.variables.extensions.yield.VariableYield;
import parts.lost.calculationsystem.variables.registry.types.VariableTemplate;

import java.util.Scanner;

public class App {

	private static class InvertMode extends Mode<Boolean> {

		protected InvertMode(Boolean currentState) {
			super(currentState);
		}
	}

	private static class MaxGeneratorTemplate extends GeneratorTemplate implements Moddable<Boolean> {

		private Mode<Boolean> mode = null;

		private static final GenOperation max = values -> {
			double max = values[0].value().getDouble();
			for (int i = 1; i < values.length; ++i) {
				double current = values[i].value().getDouble();
				if (current > max) {
					max = current;
				}
			}
			return new Value(max);
		};

		private static final GenOperation inverted = values -> {
			double min = values[0].value().getDouble();
			for (int i = 1; i < values.length; i++) {
				double current = values[i].value().getDouble();
				if ( current < min)
					min = current;
			}

			return new Value(min);
		};

		public MaxGeneratorTemplate() {
			super("max", -1, max);
		}

		@Override
		public void setMode(Mode<Boolean> mode) {
			this.mode = mode;
		}

		@Override
		public GenOperation getOperation() {
			if (mode != null && mode.getCurrentState())
				return inverted;
			else
				return max;
		}
	}

	public static void main(String[] args) {
		Calculate calculate = new Calculate();
		MaxGeneratorTemplate maxGeneratorItem = new MaxGeneratorTemplate();
		calculate.getRegistry().add(maxGeneratorItem);
		InvertMode invertMode = new InvertMode(false);
		maxGeneratorItem.setMode(invertMode);

		Variables.quickSetup(calculate);
		Variables.addVariable(calculate, new VariableTemplate("x", new Value(3d)));

		VariableCalculation calculation = calculate.interpolate("3*x");
		System.out.println(calculation.solve());
		calculation.setVariable("x", 3);
		System.out.println(calculation.solve());

		//System.out.println(calculate.calculate("max(55+5*4, 44, 20, 2*5) + 5"));
		//System.out.println(calculate.calculate("max(1, max(5, 7) + 1)"));

		Defaults.RADIANS_DEGREES_MODE.setState(RD.DEGREES);
		calculate.interpolate("4+(55/5");
		calculate.interpolate("max(55+5*4, 44, 20, 2*5) + 5");
		calculate.interpolate("4 * sin(4)");
		calculate.interpolate("45*5");
		System.out.println(calculate.calculate("+max(-3, -4)"));
		System.out.println(	calculate.calculate("-5+-4*max(-4--3, -2)"));
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				System.out.print("Enter equation (Enter q to quit): ");
				String line = scanner.nextLine();

				if (line.toLowerCase().equals("q")) {
					break;
				} else if (line.toLowerCase().contains("$radians")) {
					Defaults.RADIANS_DEGREES_MODE.setState(RD.RADIANS);
					continue;
				} else if (line.toLowerCase().contains("$degrees")) {
					Defaults.RADIANS_DEGREES_MODE.setState(RD.DEGREES);
					continue;
				} else if (line.toLowerCase().contains("$invert")) {
					invertMode.setState(!invertMode.getCurrentState());
					continue;
				} else if (line.toLowerCase().contains("$define")) {
					String[] strings = line.split(" ");
					System.out.println("Defining variable \"" + strings[1] + "\": " + strings[2]);
					calculate.getRegistry().add(new ConstantTemplate(strings[1], new Value(Double.parseDouble(strings[2]))));
					continue;
				}

				long time = System.nanoTime();
				double value = calculate.calculate(line);
				long end = System.nanoTime();
				System.out.println("Value: " + value);
				System.out.println("Runtime: " + ((end - time) / 1000000.0) + "ms");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

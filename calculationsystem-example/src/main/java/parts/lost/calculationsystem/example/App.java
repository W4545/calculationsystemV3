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

import parts.lost.calcsystem.Calculate;
import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.types.Value;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		Calculate calculate = new Calculate();
		calculate.getRegistry().add(new GeneratorItem("max", -1, value -> {
			double max = value[0].value().getDouble();
			for (int i = 1; i < value.length; ++i) {
				double current = value[i].value().getDouble();
				if (current > max) {
					max = current;
				}
			}
			return new Value(max);
		}));


		//System.out.println(calculate.calculate("max(55+5*4, 44, 20, 2*5) + 5"));
		//System.out.println(calculate.calculate("max(1, max(5, 7) + 1)"));
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

				if (line.toLowerCase().equals("q"))
					break;

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

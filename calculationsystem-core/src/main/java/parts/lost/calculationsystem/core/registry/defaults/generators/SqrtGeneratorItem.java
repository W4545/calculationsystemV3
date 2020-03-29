package parts.lost.calculationsystem.core.registry.defaults.generators;

// Name: Jack Young
// Date: 3/29/2020

import parts.lost.calculationsystem.core.registry.types.GeneratorItem;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class SqrtGeneratorItem extends GeneratorItem {

	public static final GenOperation SQRT_OPERATION = values -> new Value(Math.sqrt(values[0].value().getDouble()));

	public SqrtGeneratorItem() {
		super("sqrt", 1, SQRT_OPERATION);
	}
}

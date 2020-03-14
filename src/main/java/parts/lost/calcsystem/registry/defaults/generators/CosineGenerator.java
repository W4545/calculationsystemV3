package parts.lost.calcsystem.registry.defaults.generators;

// Name: Jack Young
// Date: 3/13/2020

import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.types.operations.GenOperation;
import parts.lost.calcsystem.types.operations.defaults.CosineGenOperation;

public class CosineGenerator extends GeneratorItem {
	public CosineGenerator() {
		super("cos", 1, CosineGenOperation.COSINE_GEN_OPERATION);
	}
}

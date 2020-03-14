package parts.lost.calcsystem.registry.defaults.generators;

// Name: Jack Young
// Date: 3/13/2020

import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.types.operations.defaults.SineGenOperation;

public class SineGenerator extends GeneratorItem {
    public SineGenerator() {
        super("sin", 1, SineGenOperation.SINE_GEN_OPERATION);
    }
}

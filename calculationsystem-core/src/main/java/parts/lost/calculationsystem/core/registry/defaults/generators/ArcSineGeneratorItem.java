package parts.lost.calculationsystem.core.registry.defaults.generators;

import parts.lost.calculationsystem.core.Conversions;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class ArcSineGeneratorItem extends RDGeneratorItem {

    public static final GenOperation SINE_GENERATOR_OPERATION_RADIANS = values -> new Value(Math.asin(values[0].value().getDouble()));

    public static final GenOperation SINE_GENERATOR_OPERATION_DEGREES = values -> new Value(Conversions.toDegrees(Math.asin(values[0].value().getDouble())));

    public ArcSineGeneratorItem() {
        super("arcsin", 1, SINE_GENERATOR_OPERATION_RADIANS, SINE_GENERATOR_OPERATION_DEGREES);
    }
}

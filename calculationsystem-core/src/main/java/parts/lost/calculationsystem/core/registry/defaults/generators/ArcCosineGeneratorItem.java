package parts.lost.calculationsystem.core.registry.defaults.generators;

import parts.lost.calculationsystem.core.Conversions;
import parts.lost.calculationsystem.core.types.Value;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class ArcCosineGeneratorItem extends RDGeneratorItem {

    public static GenOperation COSINE_GEN_OPERATION_RADIANS = values -> new Value(Math.acos(values[0].value().getDouble()));

    public static GenOperation COSINE_GEN_OPERATION_DEGREES = values -> new Value(Conversions.toDegrees(Math.acos(values[0].value().getDouble())));

    public ArcCosineGeneratorItem() {
        super("arccos", 1, COSINE_GEN_OPERATION_RADIANS, COSINE_GEN_OPERATION_DEGREES);
    }
}

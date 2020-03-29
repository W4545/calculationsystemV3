package parts.lost.calculationsystem.core.registry.defaults.generators;

import parts.lost.calculationsystem.core.registry.Moddable;
import parts.lost.calculationsystem.core.registry.Mode;
import parts.lost.calculationsystem.core.registry.defaults.modes.enums.RD;
import parts.lost.calculationsystem.core.registry.types.GeneratorItem;
import parts.lost.calculationsystem.core.types.operations.GenOperation;

public class RDGeneratorItem extends GeneratorItem implements Moddable<RD> {

    private GenOperation radians;
    private GenOperation degrees;

    private Mode<RD> mode = null;

    public RDGeneratorItem(String identifier, int argumentCount, GenOperation radians, GenOperation degrees) {
        super(identifier, argumentCount, radians);
        this.radians = radians;
        this.degrees = degrees;
    }

    @Override
    public void setMode(Mode<RD> mode) {
        this.mode = mode;
    }

    @Override
    public GenOperation getOperation() {
        return mode != null && mode.getCurrentState().equals(RD.DEGREES) ? degrees : radians;
    }
}

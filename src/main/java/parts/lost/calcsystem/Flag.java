package parts.lost.calcsystem;

import parts.lost.calcsystem.registry.types.GeneratorItem;
import parts.lost.calcsystem.registry.types.OperatorItem;
import parts.lost.calcsystem.types.Generator;
import parts.lost.calcsystem.types.Value;

public class Flag {

    private Object object;

    public Flag(Object object) {
        this.object = object;
    }

    public boolean isNumber() {
        return object instanceof Value;
    }

    public boolean isOperator() {
        return object instanceof OperatorItem;
    }

    public boolean isGeneratorItem() {
        return object instanceof GeneratorItem;
    }

    public boolean isOpenParentheses() {
        return object.equals("(");
    }

    public boolean isCloseParentheses() {
        return object.equals(")");
    }

    public boolean isComma() {
        return object.equals(",");
    }

    public boolean isGenerator() {
        return object instanceof Generator;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Flag{" +
                object +
                '}';
    }
}

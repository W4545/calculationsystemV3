package parts.lost.calcsystem;

import parts.lost.calcsystem.registry.GeneratorItem;
import parts.lost.calcsystem.registry.OperatorItem;
import parts.lost.calcsystem.types.Number;

class Flag {

    private Object object;

    public Flag(Object object) {
        this.object = object;
    }

    public boolean isNumber() {
        return object instanceof Number;
    }

    public boolean isOperator() {
        return object instanceof OperatorItem;
    }

    public boolean isGenerator() {
        return object instanceof GeneratorItem;
    }

    public boolean isOpenParentheses() {
        return object.equals("(");
    }

    public boolean isCloseParentheses() {
        return object.equals(")");
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

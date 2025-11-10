package model.expression;

import model.adts.IMyDictionary;
import model.value.IValue;

public record ConstantExpression(IValue value) implements IExpression {
    @Override
    public IValue evaluate(IMyDictionary<String,IValue> symTable) {
        return value;
    }
    @Override
    public String toString() {
        return value.toString();
    }
}

package model.expression;

import exception.MyException;
import exception.NotDefinedException;
import model.value.IValue;
import model.adts.IMyDictionary;

public record VariableExpression(String name) implements IExpression {
    @Override
    public IExpression deepCopy() {
        return new VariableExpression(name);
    }
    @Override
    public IValue evaluate(IMyDictionary<String,IValue> symTable) throws Exception {
        if (!symTable.isDefined(name)) {
            throw new NotDefinedException("Variable '" + name + "' is not defined.");
        }
        return symTable.getValue(name);
    }
    @Override
    public String toString() {
        return name;
    }
}

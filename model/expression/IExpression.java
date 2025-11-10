package model.expression;

import exception.MyException;
import model.value.IValue;
import model.adts.IMyDictionary;

public interface IExpression {
    IValue evaluate(IMyDictionary<String,IValue> symTable) throws Exception;
    IExpression deepCopy();
}

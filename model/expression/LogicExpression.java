package model.expression;

import exception.MyException;
import model.adts.IMyDictionary;
import model.type.BooleanType;
import model.value.IValue;
import model.value.BooleanValue;

public record LogicExpression(String operator, IExpression leftExpression, IExpression rightExpression) implements IExpression {
    @Override
    public String toString() {
        return leftExpression.toString() + " " + operator + " " + rightExpression.toString();
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symTable) throws Exception {
        IValue leftValue = leftExpression.evaluate(symTable);
        IValue rightValue = rightExpression.evaluate(symTable);

        if (!leftValue.getType().equals(new BooleanType())) {
            throw new MyException("First operand is not a boolean.");
        }

        if (!rightValue.getType().equals(new BooleanType())) {
            throw new MyException("Second operand is not a boolean.");
        }

        boolean leftBool = ((BooleanValue) leftValue).value();
        boolean rightBool = ((BooleanValue) rightValue).value();

        return switch (operator) {
            case "&&" -> new BooleanValue(leftBool && rightBool);
            case "||" -> new BooleanValue(leftBool || rightBool);
            default -> throw new MyException("Unknown logic operator: " + operator);
        };
    }
}

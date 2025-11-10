package model.expression;

import exception.DivisionByZeroException;
import exception.MyException;
import model.adts.IMyDictionary;
import model.type.BooleanType;
import model.type.IType;
import model.type.IntegerType;
import model.value.IValue;
import model.value.IntegerValue;

public record ArithmeticExpression(String operator, IExpression leftExpression, IExpression rightExpression) implements IExpression {
    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(operator, leftExpression.deepCopy(), rightExpression.deepCopy());
    }
    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symTable) throws Exception {
        IValue leftValue = leftExpression.evaluate(symTable);
        IValue rightValue = rightExpression.evaluate(symTable);

        if (!leftValue.getType().equals(new IntegerType())) {
            throw new MyException("First operand is not an integer.");
        }
        if (!rightValue.getType().equals(new IntegerType())) {
            throw new MyException("Second operand is not an integer.");
        }

        int leftInt = ((IntegerValue) leftValue).value();
        int rightInt = ((IntegerValue) rightValue).value();

        return switch (operator) {
            case "+" -> new IntegerValue(leftInt + rightInt);
            case "-" -> new IntegerValue(leftInt - rightInt);
            case "*" -> new IntegerValue(leftInt * rightInt);
            case "/" -> {
                if (rightInt == 0) {
                    throw new DivisionByZeroException("Division by zero");
                }
                yield new IntegerValue(leftInt / rightInt);
            }
            default -> throw new MyException("Unknown arithmetic operator: " + operator);
        };
    }


    @Override
    public String toString() {
        return leftExpression.toString() + " " + operator + " " + rightExpression.toString();
    }

}

package model.expression;

import exception.TypeMissMatchException;
import model.adts.IMyDictionary;
import model.type.IntegerType;
import model.value.IValue;
import model.value.BooleanValue;
public record RelationalExpression(String operator, IExpression leftExpression, IExpression rightExpression) implements IExpression {

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symTable) throws Exception {
        IValue leftValue = leftExpression.evaluate(symTable);
        IValue rightValue = rightExpression.evaluate(symTable);

        if(!leftValue.getType().equals(new IntegerType()) || !rightValue.getType().equals(new IntegerType())) {
            throw new TypeMissMatchException("Type mismatch in relational expression: " + this.toString());
        }
        int leftInt = ((model.value.IntegerValue) leftValue).value();
        int rightInt = ((model.value.IntegerValue) rightValue).value();

        return switch (operator) {
            case "<" -> new BooleanValue(leftInt < rightInt);
            case "<=" -> new BooleanValue(leftInt <= rightInt);
            case "==" -> new BooleanValue(leftInt == rightInt);
            case "!=" -> new BooleanValue(leftInt != rightInt);
            case ">" -> new BooleanValue(leftInt > rightInt);
            case ">=" -> new BooleanValue(leftInt >= rightInt);
            default -> throw new TypeMissMatchException("Invalid relational operator: " + operator);
        };
    }

    @Override
    public String toString() {
        return leftExpression.toString() + " " + operator + " " + rightExpression.toString();
    }
}

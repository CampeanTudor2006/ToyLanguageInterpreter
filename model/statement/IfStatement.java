package model.statement;


import exception.TypeMissMatchException;
import model.expression.IExpression;
import model.value.IValue;
import model.value.BooleanValue;

public record IfStatement(IExpression condition, IStatement thenBranch, IStatement elseBranch) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IValue result = condition.evaluate(state.getSymTable());
        if (result instanceof BooleanValue(boolean value)) {
            if (value) {
                state.getExeStack().push(thenBranch);
            } else {
                state.getExeStack().push(elseBranch);
            }
        } else {
            throw new TypeMissMatchException("Condition expression does not evaluate to a boolean.");
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition, thenBranch.deepCopy(), elseBranch.deepCopy());
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ") then {" + thenBranch.toString() + "} else {" + elseBranch.toString() + "}";
    }
}

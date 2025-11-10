package model.statement;

import model.expression.IExpression;

public record PrintStatement(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        state.getOut().add(expression.evaluate(state.getSymTable()));
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}

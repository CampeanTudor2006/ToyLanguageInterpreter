package model.statement;

import exception.MyException;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        // This statement does nothing and simply returns the current state
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }
}

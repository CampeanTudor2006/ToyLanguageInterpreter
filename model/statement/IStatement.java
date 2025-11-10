package model.statement;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;
    IStatement deepCopy();
}

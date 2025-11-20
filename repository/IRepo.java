package repository;

import exception.MyException;
import model.statement.ProgramState;

public interface IRepo {
    ProgramState getCurrentProgram() throws MyException;
    void addProgram(ProgramState program);
    void logProgramState() throws MyException;
}

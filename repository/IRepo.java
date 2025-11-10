package repository;

import model.statement.ProgramState;

public interface IRepo {
    ProgramState getCurrentProgram() throws Exception;
    void addProgram(ProgramState program);
    void logProgramState() throws Exception;
}

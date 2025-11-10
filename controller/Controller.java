package controller;

import exception.MyException;
import model.adts.IExecutionStack;
import model.statement.ProgramState;
import model.statement.IStatement;
import repository.IRepo;

public class Controller {
    private final IRepo repository;
    private boolean displayFlag = false;

    public Controller(IRepo repository) {
        this.repository = repository;
    }
    public ProgramState oneStep(ProgramState state) throws Exception {
        IExecutionStack<IStatement> stk = state.getExeStack();

        if (stk.isEmpty()) {
            throw new MyException("Program state stack is empty.");
        }

        IStatement crtStmt = stk.pop();
        return crtStmt.execute(state);
    }
    public void allSteps() throws Exception {
        ProgramState prgState = repository.getCurrentProgram();


        repository.logProgramState();
        while (!prgState.getExeStack().isEmpty()) {
            prgState = oneStep(prgState);

            repository.logProgramState();
        }

        if (displayFlag) {
            System.out.println(prgState);
        }
    }
    public void DisplayCurrPrgState(ProgramState state) throws MyException {
        if (displayFlag) {
            System.out.println("--- Current Program State ---");
            System.out.println(state.toString());
            System.out.println("-----------------------------");
        }
    }
    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }
}

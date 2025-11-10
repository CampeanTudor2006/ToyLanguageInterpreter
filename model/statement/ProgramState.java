package model.statement;

import model.adts.*;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.util.Map;

public class ProgramState{
    private IExecutionStack<IStatement> exeStack;
    private IMyDictionary<String, IValue> symTable;
    private IOut<IValue> out;
    private IMyDictionary<StringValue, BufferedReader> fileTable;


    private IStatement originalProgram;

    public ProgramState(IExecutionStack<IStatement> stk,
                        IMyDictionary<String, IValue> symtbl,
                        IOut<IValue> ot,
                        IMyDictionary<StringValue, BufferedReader> fileTbl,
                        IStatement prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = fileTbl;
        this.originalProgram = prg.deepCopy();

        // Logica din constructor: adaugă programul pe stivă
        stk.push(prg);
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IMyDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IExecutionStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IExecutionStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public IMyDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(IMyDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public IOut<IValue> getOut() {
        return out;
    }

    public void setOut(IOut<IValue> out) {
        this.out = out;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }
    @Override
    public String toString() {
        // Folosim StringBuilder pentru o concatenare eficientă
        StringBuilder sb = new StringBuilder();

        sb.append("ExeStack:\n");
        sb.append(exeStack.toString());

        sb.append("SymTable:\n");
        sb.append(symTable.toString());

        sb.append("Out:\n");
        sb.append(out.toString());
        sb.append("\n");

        sb.append("FileTable:\n");
        for (StringValue key : fileTable.getContent().keySet()) {
            sb.append(key.toString()).append("\n");
        }

        sb.append("-----------------------\n");
        return sb.toString();
    }

    public ProgramState deepCopy() {
        IExecutionStack<IStatement> newStack = this.exeStack.copy();

        IMyDictionary<String, IValue> newSymTable = this.symTable.copy();

        IOut<IValue> newOut = this.out.copy();

        IMyDictionary<StringValue, BufferedReader> sameFileTable = this.fileTable;

        return new ProgramState(newStack, newSymTable, newOut, sameFileTable, this.originalProgram);
    }
}

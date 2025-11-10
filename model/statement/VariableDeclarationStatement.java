package model.statement;

import exception.MyException;
import model.type.IType;
import model.value.IValue;
import model.adts.IMyDictionary;


public record VariableDeclarationStatement(IType type, String name) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyDictionary<String, IValue> symbolTable = state.getSymTable();


        if (symbolTable.isDefined(name)) {
            throw new MyException("Variable already defined: " + name);
        }

        IValue defaultValue = type.defaultValue();
        symbolTable.update(name, defaultValue);

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(type, name);
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}

package model.statement;

import exception.FileException;
import exception.NotDefinedException;
import exception.TypeMissMatchException;
import model.expression.IExpression;
import model.adts.IMyDictionary;
import model.type.IntegerType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntegerValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{

    private final IExpression expression;
    private final String varName;

    public ReadFileStatement(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyDictionary<String, IValue> symTable = state.getSymTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!symTable.isDefined(varName)) {
            throw new NotDefinedException("Error: Variable '" + varName + "' is not defined in SymTable.");
        }

        IValue expValue = symTable.getValue(varName);
        if (!expValue.getType().equals(new IntegerType())) {
            throw new NotDefinedException("Error: Variable '" + varName + "' is not of type Integer.");
        }
        IValue evaluatedValue = expression.evaluate(symTable);
        if (!evaluatedValue.getType().equals(new StringType())) {
            throw new TypeMissMatchException("Error: Expression does not evaluate to a String.");
        }

        StringValue fileNameValue = (StringValue) evaluatedValue;
        if (!fileTable.isDefined(fileNameValue)) {
            throw new FileException("Error: File '" + fileNameValue.value() + "' is not opened.");
        }

        BufferedReader bufferedReader = fileTable.getValue(fileNameValue);
        String line;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new FileException("Error reading from file: " + fileNameValue.value());
        }
        IntegerValue intValue;
        if (line == null) {
            intValue = new IntegerValue(0);
        }
        else {
            try {
                intValue = new IntegerValue(Integer.parseInt(line));
            } catch (NumberFormatException e) {
                throw new FileException("Error: The line read from file is not a valid integer.");
            }
        }
        symTable.update(varName, intValue);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression, varName);
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + varName + ")";
    }
}

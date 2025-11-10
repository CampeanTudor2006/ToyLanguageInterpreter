package model.statement;
import exception.FileException;
import exception.TypeMissMatchException;
import model.expression.IExpression;
import model.adts.IMyDictionary;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
public class CloseRFileStatement implements IStatement{

    private final IExpression expression;

    public CloseRFileStatement(IExpression expression) {
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IMyDictionary<String, IValue> symTable = state.getSymTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = expression.evaluate(symTable);
        if (!value.getType().equals(new StringType())) {
            throw new TypeMissMatchException("Expression must evaluate to a string.");
        }

        StringValue fileNameValue = (StringValue) value;
        if (!fileTable.isDefined(fileNameValue)) {
            throw new FileException("File not opened: " + fileNameValue.value());
        }
        BufferedReader bufferedReader = fileTable.getValue(fileNameValue);
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new FileException("Could not close file: " + fileNameValue.value());
        }
        fileTable.remove(fileNameValue);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression);
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}

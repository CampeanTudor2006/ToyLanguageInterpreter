package view;

import controller.Controller;
import model.adts.*;
import model.expression.ArithmeticExpression;
import model.expression.ConstantExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntegerType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IValue;
import model.value.IntegerValue;
import model.value.StringValue;
import repository.IRepo;
import repository.Repo;

public class Main {
    public static void main(String[] args) {

        IExecutionStack<IStatement> exeStack = new StackExecutionStack<>();
        IMyDictionary<String, IValue> symTable = new MapSymbolTable<>();
        IOut<IValue> out = new ListOut<>();
        IMyDictionary<StringValue, java.io.BufferedReader> fileTable = new MapSymbolTable<>();

        IStatement example1 = getExample1();
        ProgramState state1 = new ProgramState(exeStack, symTable, out, fileTable,example1);
        IRepo repo1 = new Repo("files/log1.txt", state1);
        Controller controller1 = new Controller(repo1);

        IStatement example2 = getExample2();
        ProgramState state2 = new ProgramState(new StackExecutionStack<>(), new MapSymbolTable<>(), new ListOut<>(), new MapSymbolTable<>(), example2);
        IRepo repo2 = new Repo("files/log2.txt", state2);
        Controller controller2 = new Controller(repo2);

        IStatement example3 = getExample3();
        ProgramState state3 = new ProgramState(new StackExecutionStack<>(), new MapSymbolTable
<>(), new ListOut<>(), new MapSymbolTable<>(), example3);
        IRepo repo3 = new Repo("files/log3.txt", state3);
        Controller controller3 = new Controller(repo3);

        IStatement example4 = getExample4_FileOperations();
        ProgramState state4 = new ProgramState(new StackExecutionStack<>(), new MapSymbolTable
<>(), new ListOut<>(), new MapSymbolTable<>(), example4);
        IRepo repo4 = new Repo("files/log4.txt", state4);
        Controller controller4 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExampleCommand("1", "Execute example 1", controller1));
        menu.addCommand(new RunExampleCommand("2", "Execute example 2", controller2));
        menu.addCommand(new RunExampleCommand("3", "Execute example 3", controller3));
        menu.addCommand(new RunExampleCommand("4", "Execute example 4", controller4));
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.show();
    }
    private static IStatement getExample1() {
        // int v; v=2; Print(v)
        return new CompoundStatement(new VariableDeclarationStatement(new IntegerType(), "v"), new CompoundStatement(
                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(2))),
                new PrintStatement(new VariableExpression("v"))
        )
        );
    }
    private static IStatement getExample2() {
        // int a; int b; a=2+3*5; b=a+1; Print(b)
        return new CompoundStatement(
                new VariableDeclarationStatement(new IntegerType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "b"),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ArithmeticExpression("+",
                                        new ConstantExpression(new IntegerValue(2)),
                                        new ArithmeticExpression("*",
                                                new ConstantExpression(new IntegerValue(3)),
                                                new ConstantExpression(new IntegerValue(5))
                                        )
                                )),
                                new CompoundStatement(
                                        new AssignmentStatement("b", new ArithmeticExpression("+",
                                                new VariableExpression("a"),
                                                new ConstantExpression(new IntegerValue(1))
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }
    private static IStatement getExample3() {
        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        return new CompoundStatement(
                new VariableDeclarationStatement(new BooleanType(), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new IntegerType(), "v"),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ConstantExpression(new BooleanValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(2))),
                                                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }
    private static IStatement getExample4_FileOperations() {
        /*
        string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc);
        closeRFile(varf)
    */
        return new CompoundStatement(
                // string varf;
                new VariableDeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(
                        // varf="test.in";
                        new AssignmentStatement("varf", new ConstantExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                // openRFile(varf);
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        // int varc;
                                        new VariableDeclarationStatement(new IntegerType(), "varc"),
                                        new CompoundStatement(
                                                // readFile(varf,varc);
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        // print(varc);
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                // readFile(varf,varc);
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        // print(varc);
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        // closeRFile(varf)
                                                                        new CloseRFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }
}

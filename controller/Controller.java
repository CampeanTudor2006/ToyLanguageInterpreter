package controller;

import exception.MyException;
import model.adts.IExecutionStack;
import model.statement.ProgramState;
import model.statement.IStatement;
import repository.IRepo;

import model.value.RefValue;
import model.value.IValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private final IRepo repository;
    private boolean displayFlag = false;

    public Controller(IRepo repository) {
        this.repository = repository;
    }
    public ProgramState oneStep(ProgramState state) throws MyException {
        IExecutionStack<IStatement> stk = state.getExeStack();

        if (stk.isEmpty()) {
            throw new MyException("Program state stack is empty.");
        }

        IStatement crtStmt = stk.pop();
        return crtStmt.execute(state);
    }
    public void allSteps() throws MyException {
        ProgramState prgState = repository.getCurrentProgram();
        repository.logProgramState();

        while (!prgState.getExeStack().isEmpty()) {
            prgState = oneStep(prgState);
            //Garbage collector call
            prgState.getHeap().setContent(
                    safeGarbageCollector(
                            getAddrFromHeap(
                                    getAddrFromSymTable(prgState.getSymTable().getContent().values()),
                                    prgState.getHeap().getContent()
                            ),
                            prgState.getHeap().getContent()
                    )
            );

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

    //Save the addresses from the symbol table that are RefValues
    private List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)//Keep only RefValues
                .map(v -> { RefValue v1 = (RefValue) v; return v1.address(); })//Get their addresses
                .collect(Collectors.toList());//Collect as a list
    }
    //From a list of addresses, get all the addresses that are referenced from the heap
    private List<Integer> getAddrFromHeap(List<Integer> symTableAddr, Map<Integer, IValue> heap) {

        boolean change = true;
        //Traverse the addresses from the symbol table
        List<Integer> newAddr = symTableAddr;
        //Keep adding new addresses until no new address is found
        while (change) {
            List<Integer> nextAddr = new ArrayList<>(newAddr);
            List<Integer> currentAddr = newAddr;

            heap.entrySet().stream()
                    //Keep only the entries whose key (address) is in the current address list(symTable + previously found addresses)
                    .filter(e -> currentAddr.contains(e.getKey()))
                    .forEach(e -> {
                        //If the value is a RefValue, get its address and add it to the next address list
                        if (e.getValue() instanceof RefValue) {
                            RefValue refValue = (RefValue) e.getValue();
                            int refAddr = refValue.address();
                            if (!nextAddr.contains(refAddr)) {
                                nextAddr.add(refAddr);
                            }
                        }
                    });

            if (nextAddr.size() > newAddr.size()) {
                newAddr = nextAddr;
            } else {
                change = false;
            }
        }
        return newAddr;
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> activeAddresses, Map<Integer, IValue> heap) {
        //Keep only the heap entries whose addresses are in the active addresses list
        return heap.entrySet().stream()
                .filter(e -> activeAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}


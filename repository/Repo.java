    package repository;

    import exception.MyException;
    import model.statement.ProgramState;

    import java.io.BufferedWriter;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.ArrayList;
    import java.util.List;

    public class Repo implements IRepo {
        private final String logFilePath;
        private final List<ProgramState> programStates;
        public Repo(String logFilePath, ProgramState initialProgram) {
            this.logFilePath = logFilePath;
            this.programStates = new ArrayList<>();
            this.programStates.add(initialProgram);
        }

        @Override
        public ProgramState getCurrentProgram() throws Exception {
            if (programStates.isEmpty()) {
                throw new MyException("Repository is empty. No program state found.");
            }
            return programStates.getFirst();
        }

        @Override
        public void addProgram(ProgramState program) {
            programStates.add(program);
        }

       @Override
        public void logProgramState() throws Exception {
            ProgramState currentState = getCurrentProgram();
            try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
                logFile.println(currentState.toString());
            } catch (IOException e) {
                throw new MyException("Error writing to log file: " + e.getMessage());
            }
        }
    }


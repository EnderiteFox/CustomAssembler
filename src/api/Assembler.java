package api;

import java.util.List;

public interface Assembler {
    short readInstruction(String instruction) throws IllegalArgumentException;

    List<Short> readProgram(List<String> program) throws InternalError;

    void printAssemblyProgram(List<Short> assemblyProgram);
}

package fr.enderitefox.redstoneassembler.api;

import java.util.List;

public interface Assembler {
    short readInstruction(String instruction) throws IllegalArgumentException;

    List<Short> readProgram(List<String> program) throws InternalError, IllegalArgumentException;

    void printAssemblyProgram(List<Short> assemblyProgram);
}

package fr.enderitefox.redstoneassembler.api;

public interface InstructionReader {
    short readInstruction(String instruction) throws IllegalArgumentException;
}

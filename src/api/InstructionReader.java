package api;

public interface InstructionReader {
    short readInstruction(String instruction) throws IllegalArgumentException;
}

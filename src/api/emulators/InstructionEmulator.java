package api.emulators;

public interface InstructionEmulator<T extends AssemblyLanguageEmulator<?>> {
    void emulateInstruction(short instruction, T emulator) throws ProgramInterruptException;
}

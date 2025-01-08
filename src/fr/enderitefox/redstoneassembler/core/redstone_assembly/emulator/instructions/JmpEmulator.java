package fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.instructions;

import fr.enderitefox.redstoneassembler.api.emulators.InstructionEmulator;
import fr.enderitefox.redstoneassembler.api.emulators.ProgramInterruptException;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class JmpEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        emulator.setProgramCounter((short) ((instruction & 0x03FF) - 1));
    }
}

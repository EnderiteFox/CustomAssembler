package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import api.emulators.ProgramInterruptException;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class RshEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        byte regA = (byte) ((instruction & 0x0F00) >> 8);
        byte regB = (byte) ((instruction & 0x00F0) >> 4);
        emulator.setReg(regA, (byte) ((emulator.getReg(regB) >> 1) & 0x7F));
    }
}

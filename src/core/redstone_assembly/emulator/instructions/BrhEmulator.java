package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import api.emulators.ProgramInterruptException;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class BrhEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        byte condition = (byte) ((instruction & 0x0C00) >> 10);
        byte address = (byte) (instruction & 0x00FF);
        if (condition == emulator.getFlags()) emulator.setProgramCounter((short) (address - 1));
    }
}

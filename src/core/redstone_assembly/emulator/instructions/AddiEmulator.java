package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import api.emulators.ProgramInterruptException;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class AddiEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        byte reg = (byte) ((instruction & 0x0F00) >> 8);
        byte immediate = (byte) (instruction & 0x00FF);
        boolean overflow = (emulator.getReg(reg) + immediate) > 127;
        boolean zero = (byte) (emulator.getReg(reg) + immediate) == 0;
        emulator.setFlags(((overflow ? 1 : 0) << 1) | (zero ? 1 : 0));
        emulator.setReg(reg, (byte) (emulator.getReg(reg) + immediate));
    }
}

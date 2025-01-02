package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import api.emulators.ProgramInterruptException;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class SubEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        byte regA = (byte) ((instruction & 0x0F00) >> 8);
        byte regB = (byte) ((instruction & 0x00F0) >> 4);
        byte regC = (byte) (instruction & 0x000F);
        int sub = emulator.getReg(regB) - emulator.getReg(regC);
        boolean overflow = sub < -128 || sub > 127;
        boolean zero = (byte) sub == 0;
        emulator.setFlags(((overflow ? 1 : 0) << 1) | (zero ? 1 : 0));
        emulator.setReg(regA, (byte) (emulator.getReg(regB) - emulator.getReg(regC)));
    }
}

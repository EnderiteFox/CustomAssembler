package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class AddEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) {
        byte regA = (byte) ((instruction & 0x0F00) >> 8);
        byte regB = (byte) ((instruction & 0x00F0) >> 4);
        byte regC = (byte) (instruction & 0x000F);
        emulator.setReg(regA, (byte) (emulator.getReg(regB) + emulator.getReg(regC)));
    }
}

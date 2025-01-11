package fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.instructions;

import fr.enderitefox.redstoneassembler.api.emulators.InstructionEmulator;
import fr.enderitefox.redstoneassembler.api.emulators.ProgramInterruptException;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class SubEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        byte regA = (byte) ((instruction & 0x0F00) >> 8);
        byte regB = (byte) ((instruction & 0x00F0) >> 4);
        byte regC = (byte) (instruction & 0x000F);
        int sub = emulator.getReg(regB) - emulator.getReg(regC);
        boolean zero = (byte) sub == 0;
        // Because substraction is using overflow, the overflow flag will always be set to true
        emulator.setFlags(0b10 | (zero ? 1 : 0));
        emulator.setReg(regA, (byte) (emulator.getReg(regB) - emulator.getReg(regC)));
    }
}

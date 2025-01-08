package fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.instructions;

import fr.enderitefox.redstoneassembler.api.emulators.InstructionEmulator;
import fr.enderitefox.redstoneassembler.api.emulators.ProgramInterruptException;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class AndEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        byte regA = (byte) ((instruction & 0x0F00) >> 8);
        byte regB = (byte) ((instruction & 0x00F0) >> 4);
        byte regC = (byte) (instruction & 0x000F);
        emulator.setFlags((emulator.getReg(regB) & emulator.getReg(regC)) == 0 ? 1 : 0);
        emulator.setReg(regA, (byte) (emulator.getReg(regB) & emulator.getReg(regC)));
    }
}

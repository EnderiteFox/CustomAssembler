package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class NopEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) {}
}

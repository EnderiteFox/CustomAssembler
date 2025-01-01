package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import api.emulators.ProgramInterruptException;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class RetEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        emulator.setProgramCounter((short) (emulator.getCallStack().pop() - 1));
    }
}

package core.redstone_assembly.emulator.instructions;

import api.emulators.InstructionEmulator;
import api.emulators.ProgramInterruptException;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;

public class CallEmulator implements InstructionEmulator<RedstoneAssemblyEmulator> {
    @Override
    public void emulateInstruction(short instruction, RedstoneAssemblyEmulator emulator) throws ProgramInterruptException {
        emulator.getCallStack().push((short) (emulator.getProgramCounter() + 1));
        emulator.setProgramCounter((short) ((instruction & 0x03FF) - 1));
    }
}

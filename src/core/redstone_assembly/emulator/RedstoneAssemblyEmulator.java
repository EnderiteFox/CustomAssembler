package core.redstone_assembly.emulator;

import api.emulators.AssemblyLanguageEmulator;
import api.emulators.InstructionEmulator;
import api.emulators.ProgramInterruptException;
import core.redstone_assembly.emulator.instructions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@SuppressWarnings("FieldCanBeLocal")
public class RedstoneAssemblyEmulator implements AssemblyLanguageEmulator<RedstoneAssemblyResult> {
    private final Map<Byte, Byte> registryBank = new HashMap<>();
    private final Map<Byte, Byte> ram = new HashMap<>();

    private final Map<Byte, InstructionEmulator<RedstoneAssemblyEmulator>> instructionEmulators = new HashMap<>();

    private int programCounter = 0;
    private final Stack<Short> callStack = new Stack<>();
    private int flags = 0;

    private final int MAX_PROGRAM_LOOP_COUNT = 10;
    private final int MAX_INSTRUCTION_EXECUTION = 1024 * 100;
    private int programLoops = 0;
    private int instructionsExecuted = 0;
    private final boolean loopDetection;

    public RedstoneAssemblyEmulator(boolean loopDetection) {
        this.loopDetection = loopDetection;

        registerInstructionEmulator(0, new NopEmulator());
        registerInstructionEmulator(1, new HaltEmulator());
        registerInstructionEmulator(2, new AddEmulator());
        registerInstructionEmulator(3, new AddiEmulator());
        registerInstructionEmulator(4, new AndEmulator());
        registerInstructionEmulator(5, new RshEmulator());
        registerInstructionEmulator(6, new NorEmulator());
        registerInstructionEmulator(7, new XorEmulator());
        registerInstructionEmulator(8, new SubEmulator());
        registerInstructionEmulator(9, new JmpEmulator());
        registerInstructionEmulator(10, new BrhEmulator());
        registerInstructionEmulator(11, new CallEmulator());
        registerInstructionEmulator(12, new RetEmulator());
        registerInstructionEmulator(13, new LodEmulator());
        registerInstructionEmulator(14, new WriEmulator());
    }

    public RedstoneAssemblyEmulator() {
        this(true);
    }

    private void registerInstructionEmulator(int opcode, InstructionEmulator<RedstoneAssemblyEmulator> emulator) {
        instructionEmulators.put((byte) opcode, emulator);
    }

    @Override
    public RedstoneAssemblyResult emulateProgram(List<Short> program) throws InternalError {
        while (true) {
            if (programCounter >= program.size()) {
                programCounter++;
                if (programCounter == 1024) {
                    programCounter = 0;
                    programLoop();
                }
                continue;
            }
            short instruction = program.get(programCounter);
            byte opcode = (byte) ((instruction & 0xF000) >> 12);
            if (!instructionEmulators.containsKey(opcode)) throw new InternalError(
                "No emulator registered for operation " + opcode
            );
            try {
                instructionEmulators.get(opcode).emulateInstruction(instruction, this);
            }
            catch (ProgramInterruptException e) {
                break;
            }
            programCounter++;
            if (programCounter == 1024) {
                programCounter = 0;
                programLoop();
            }
            instructionsExecuted++;
            if (instructionsExecuted >= MAX_INSTRUCTION_EXECUTION && loopDetection) throw new InternalError(
                "Max amount of instruction executions reached (" + MAX_INSTRUCTION_EXECUTION + "), program is probably looping"
            );
        }
        return new RedstoneAssemblyResult(registryBank, ram);
    }

    private void programLoop() {
        programLoops++;
        if (programLoops >= MAX_PROGRAM_LOOP_COUNT && loopDetection) throw new InternalError(
            "Max amount of program loops reached (" + MAX_PROGRAM_LOOP_COUNT + "), program is probably looping"
        );
    }

    public short getProgramCounter() {
        return (short) programCounter;
    }

    public void setProgramCounter(short newProgramCounter) {
        this.programCounter = newProgramCounter;
    }

    public Stack<Short> getCallStack() {
        return callStack;
    }

    public byte getReg(byte registry) {
        if (registry < 0 || registry > 15) throw new InternalError("Registry " + registry + " doesn't exist");
        return registryBank.getOrDefault(registry, (byte) 0);
    }

    public void setReg(byte registry, byte value) {
        if (registry < 0 || registry > 15) throw new InternalError("Registry " + registry + " doesn't exist");
        if (registry == 0) return;
        registryBank.put(registry, value);
    }

    public byte getRam(byte address) {
        return ram.getOrDefault(address, (byte) 0);
    }

    public void setRam(byte address, byte value) {
        ram.put(address, value);
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}

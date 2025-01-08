package fr.enderitefox.redstoneassembler.core.redstone_assembly.instruction_readers;

import fr.enderitefox.redstoneassembler.api.InstructionReader;
import fr.enderitefox.redstoneassembler.api.OperationTable;

public abstract class AbstractInstructionReader implements InstructionReader {
    protected final OperationTable operationTable;
    protected final String instructionRegex;

    protected final short INVALID_INSTRUCTION = (short) 0x2FFF;

    protected AbstractInstructionReader(OperationTable operationTable, String instructionRegex) {
        this.operationTable = operationTable;
        this.instructionRegex = instructionRegex;
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException {
        if (!instruction.matches(instructionRegex)) {
            throw new IllegalArgumentException("Instruction does not match regex /" + instructionRegex + "/");
        }
        String[] args = instruction.trim().split(" ");
        if (args.length == 0) throw new IllegalArgumentException("Instruction has no arguments");
        String operation = args[0].toLowerCase();
        if (operationTable.getOperationCode(operation).isEmpty()) {
            throw new IllegalArgumentException("Unknown operation: " + operation);
        }
        return 0;
    }

    protected short buildInstruction(short op, short regA, short regB, short regC) {
        return (short) ((op << 12) | (regA << 8) | (regB << 4) | regC);
    }
}

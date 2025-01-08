package fr.enderitefox.redstoneassembler.core.redstone_assembly.instruction_readers;

import fr.enderitefox.redstoneassembler.api.OperationTable;

public class ZeroInstructionReader extends AbstractInstructionReader {
    public ZeroInstructionReader(OperationTable operationTable) {
        super(operationTable, "^[a-zA-Z]+$");
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException {
        super.readInstruction(instruction);
        String[] args = instruction.split(" ");
        if (args.length != 1) throw new IllegalArgumentException(
            "Expected 1 argument, got " + args.length
        );
        if (operationTable.getOperationCode(args[0].toLowerCase()).isEmpty()) return INVALID_INSTRUCTION;
        return buildInstruction(
            operationTable.getOperationCode(args[0].toLowerCase()).get(),
            (short) 0,
            (short) 0,
            (short) 0
        );
    }
}

package core.redstone_assembly.instruction_readers;

import api.OperationTable;

public class ReducedRegisterInstructionReader extends AbstractInstructionReader {
    public ReducedRegisterInstructionReader(OperationTable operationTable) {
        super(operationTable, "^[a-zA-Z]+( r([0-9]|1[0-5])){2}$");
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException {
        super.readInstruction(instruction);
        String[] args = instruction.trim().split(" ");
        if (args.length != 3) throw new IllegalArgumentException(
            "Expected 3 arguments, got " + args.length
        );
        if (operationTable.getOperationCode(args[0].toLowerCase()).isEmpty()) return INVALID_INSTRUCTION;
        return buildInstruction(
            operationTable.getOperationCode(args[0].toLowerCase()).get(),
            Short.parseShort(args[1].substring(1)),
            Short.parseShort(args[2].substring(1)),
            (short) 0
        );
    }
}

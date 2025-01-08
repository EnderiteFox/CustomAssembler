package fr.enderitefox.redstoneassembler.core.redstone_assembly.instruction_readers;

import fr.enderitefox.redstoneassembler.api.OperationTable;

public class RegisterInstructionReader extends AbstractInstructionReader {
    public RegisterInstructionReader(OperationTable operationTable) {
        super(operationTable, "^[a-zA-Z]+(?: r(?:[0-9]|1[0-5])){3}$");
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException {
        super.readInstruction(instruction);
        String[] args = instruction.trim().split(" ");
        if (args.length != 4) throw new IllegalArgumentException(
            "Expected 4 arguments, got " + args.length
        );
        if (operationTable.getOperationCode(args[0].toLowerCase()).isEmpty()) return INVALID_INSTRUCTION;
        return buildInstruction(
            operationTable.getOperationCode(args[0].toLowerCase()).get(),
            Short.parseShort(args[1].substring(1)),
            Short.parseShort(args[2].substring(1)),
            Short.parseShort(args[3].substring(1))
        );
    }
}

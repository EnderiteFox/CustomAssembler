package fr.enderitefox.redstoneassembler.core.redstone_assembly.instruction_readers;

import fr.enderitefox.redstoneassembler.api.OperationTable;

public class MemoryAddressedInstructionReader extends AbstractInstructionReader {
    public MemoryAddressedInstructionReader(OperationTable operationTable) {
        super(operationTable, "^[a-zA-Z0-9]+(?: r(?:[0-9]|1[0-5])){2} (?:[0-9]+|0b[01]{1,4}|0x[0-9a-fA-F])$");
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException {
        super.readInstruction(instruction);
        String[] args = instruction.trim().split(" ");
        if (args.length != 4) throw new IllegalArgumentException(
            "Expected 4 arguments, got " + args.length
        );
        if (operationTable.getOperationCode(args[0]).isEmpty()) return INVALID_INSTRUCTION;
        short offset;
        if (args[3].startsWith("0b")) {
            offset = Short.parseShort(args[3].substring(2), 2);
        }
        else if (args[3].startsWith("0x")) {
            offset = Short.parseShort(args[3].substring(2), 16);
        }
        else offset = Short.parseShort(args[3]);
        if (offset > 255) throw new IllegalArgumentException(
            "Offset " + offset + " is out of bounds for [0, 15]"
        );
        return buildInstruction(
            operationTable.getOperationCode(args[0]).get(),
            Short.parseShort(args[1].substring(1)),
            Short.parseShort(args[2].substring(1)),
            offset
        );
    }
}

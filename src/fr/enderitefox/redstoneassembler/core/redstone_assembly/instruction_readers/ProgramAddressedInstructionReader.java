package fr.enderitefox.redstoneassembler.core.redstone_assembly.instruction_readers;

import fr.enderitefox.redstoneassembler.api.OperationTable;

public class ProgramAddressedInstructionReader extends AbstractInstructionReader {
    public ProgramAddressedInstructionReader(OperationTable operationTable) {
        super(operationTable, "^[a-zA-Z]+(?: 0b[01]{2})? (?:[0-9]+|0b[01]{1,10}|0x[0-9a-fA-f]{1,3})");
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException {
        super.readInstruction(instruction);
        String[] args = instruction.trim().split(" ");
        if (args.length != 3 && args.length != 2) throw new IllegalArgumentException(
            "Expected 2 or 3 arguments, got " + args.length
        );
        if (operationTable.getOperationCode(args[0].toLowerCase()).isEmpty()) return INVALID_INSTRUCTION;
        short op = operationTable.getOperationCode(args[0].toLowerCase()).get();
        op <<= 12;
        short condition = args.length == 2 ? 0 : Short.parseShort(args[1].substring(2), 2);
        condition <<= 10;
        String addressArg = args[args.length == 2 ? 1 : 2];
        short address;
        if (addressArg.startsWith("0b")) {
            address = Short.parseShort(addressArg.substring(2), 2);
        }
        else if (addressArg.startsWith("0x")) {
            address = Short.parseShort(addressArg.substring(2), 16);
        }
        else address = Short.parseShort(addressArg);
        if (address > 1023 || address < 0) throw new IllegalArgumentException(
            "Address " + address + " is out of bounds for [0, 1023]"
        );
        address &= 0x03FF;
        return (short) (op | condition | address);
    }
}

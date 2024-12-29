package core.redstone_assembly.instruction_readers;

import api.OperationTable;

public class ImmediateInstructionReader extends AbstractInstructionReader {
    public ImmediateInstructionReader(OperationTable operationTable) {
        super(operationTable, "^[a-zA-Z]+ r([0-9]|1[0-5]) (-?[0-9]+|0b[01]{1,8}|0x[0-9a-fA-F]{1,2})");
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException {
        super.readInstruction(instruction);
        String[] args = instruction.trim().split(" ");
        if (args.length != 3) throw new IllegalArgumentException(
            "Expected 3 arguments, got " + args.length
        );
        if (operationTable.getOperationCode(args[0].toLowerCase()).isEmpty()) return INVALID_INSTRUCTION;
        short op = operationTable.getOperationCode(args[0].toLowerCase()).get();
        op <<= 12;
        short regA = Short.parseShort(args[1].substring(1));
        regA <<= 8;
        short data;
        if (args[2].matches("0b[01]{1,8}")) {
            data = Short.parseShort(args[2].substring(2), 2);
        }
        else if (args[2].matches("0x[0-9a-fA-F]{1,2}")) {
            data = Short.parseShort(args[2].substring(2), 16);
        }
        else data = Short.parseShort(args[2]);
        data &= 0x00FF;
        return (short) (op | regA | data);
    }
}

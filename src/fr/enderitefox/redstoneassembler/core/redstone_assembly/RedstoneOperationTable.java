package fr.enderitefox.redstoneassembler.core.redstone_assembly;

import fr.enderitefox.redstoneassembler.api.InstructionRecord;
import fr.enderitefox.redstoneassembler.api.InstructionType;
import fr.enderitefox.redstoneassembler.api.OperationTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RedstoneOperationTable implements OperationTable {
    private final Map<String, InstructionRecord> operationTable = new HashMap<>();

    public RedstoneOperationTable() {
        registerOp("nop", 0, InstructionType.ZERO);
        registerOp("halt", 1, InstructionType.ZERO);
        registerOp("add", 2, InstructionType.REGISTER);
        registerOp("addi", 3, InstructionType.IMMEDIATE);
        registerOp("and", 4, InstructionType.REGISTER);
        registerOp("rsh", 5, InstructionType.REDUCED_REGISTER);
        registerOp("nor", 6, InstructionType.REGISTER);
        registerOp("xor", 7, InstructionType.REGISTER);
        registerOp("sub", 8, InstructionType.REGISTER);
        registerOp("jmp", 9, InstructionType.PROGRAM_ADDRESSED);
        registerOp("brh", 10, InstructionType.PROGRAM_ADDRESSED);
        registerOp("cal", 11, InstructionType.PROGRAM_ADDRESSED);
        registerOp("ret", 12, InstructionType.ZERO);
        registerOp("lod", 13, InstructionType.MEMORY_ADDRESSED);
        registerOp("wri", 14, InstructionType.MEMORY_ADDRESSED);
    }

    @Override
    public Optional<Short> getOperationCode(String operationName) {
        if (!operationTable.containsKey(operationName)) return Optional.empty();
        return Optional.of(operationTable.get(operationName).opCode());
    }

    @Override
    public Optional<InstructionType> getInstructionType(String operationName) {
        if (!operationTable.containsKey(operationName)) return Optional.empty();
        return Optional.of(operationTable.get(operationName).instructionType());
    }

    @Override
    public Optional<String> getOperationName(short operationCode) {
        for (String operation : operationTable.keySet()) {
            if (operationTable.get(operation).opCode() == operationCode) return Optional.of(operation);
        }
        return Optional.empty();
    }

    private void registerOp(String opName, int opCode, InstructionType instructionType) {
        operationTable.put(opName, new InstructionRecord((short) opCode, instructionType));
    }
}

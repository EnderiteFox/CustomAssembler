package core.redstone_assembly;

import api.InstructionReader;
import api.Assembler;
import api.InstructionType;
import api.OperationTable;
import core.redstone_assembly.instruction_readers.*;

import java.util.*;

public class RedstoneAssembler implements Assembler {
    private final Map<InstructionType, InstructionReader> readerMap = new HashMap<>();
    private final OperationTable operationTable;

    @SuppressWarnings("FieldCanBeLocal")
    private final short INVALID_INSTRUCTION = (short) 0x2FFF;

    public RedstoneAssembler(OperationTable operationTable) {
        this.operationTable = operationTable;

        readerMap.put(InstructionType.REGISTER, new RegisterInstructionReader(operationTable));
        readerMap.put(InstructionType.REDUCED_REGISTER, new ReducedRegisterInstructionReader(operationTable));
        readerMap.put(InstructionType.IMMEDIATE, new ImmediateInstructionReader(operationTable));
        readerMap.put(InstructionType.ZERO, new ZeroInstructionReader(operationTable));
        readerMap.put(InstructionType.PROGRAM_ADDRESSED, new ProgramAddressedInstructionReader(operationTable));
        readerMap.put(InstructionType.MEMORY_ADDRESSED, new MemoryAddressedInstructionReader(operationTable));
    }

    @Override
    public short readInstruction(String instruction) throws IllegalArgumentException{
        String[] args = instruction.split(" ");
        if (args.length == 0) throw new IllegalArgumentException(
            "Empty line is being read as an instruction"
        );
        String operation = args[0].toLowerCase();
        Optional<InstructionType> optionalInstructionType = operationTable.getInstructionType(operation);
        if (optionalInstructionType.isEmpty()) throw new IllegalArgumentException(
            "Unknown operation: " + operation
        );
        InstructionType instructionType = optionalInstructionType.get();
        if (!readerMap.containsKey(instructionType)) throw new IllegalArgumentException(
            "No instruction reader known for instruction type " + instructionType
        );
        InstructionReader instReader = readerMap.get(instructionType);
        return instReader.readInstruction(instruction);
    }

    @Override
    public List<Short> readProgram(List<String> program) throws InternalError {
        List<Short> assembledProgram = new ArrayList<>();
        for (String instruction : program) {
            if (instruction.startsWith("//")) continue;
            if (instruction.isBlank()) continue;
            try {
                assembledProgram.add(readInstruction(instruction));
            }
            catch (IllegalArgumentException e) {
                System.err.println("Error while reading instruction, ignoring: " + instruction);
                System.err.println(e.getMessage());
            }
        }
        if (assembledProgram.contains(INVALID_INSTRUCTION)) throw new InternalError(
            "Internal error while parsing program"
        );
        return assembledProgram;
    }

    @Override
    public void printAssemblyProgram(List<Short> assemblyProgram) {
        assemblyProgram.forEach(
            assemblyLine -> {
                for (int i = 15; i >= 0; --i) {
                    System.out.print((assemblyLine & (1 << i)) != 0 ? "1" : "0");
                }
                System.out.println();
            }
        );
    }
}

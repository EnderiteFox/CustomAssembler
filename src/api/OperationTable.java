package api;

import java.util.Optional;

public interface OperationTable {
    Optional<Short> getOperationCode(String operationName);

    Optional<InstructionType> getInstructionType(String operationName);
}

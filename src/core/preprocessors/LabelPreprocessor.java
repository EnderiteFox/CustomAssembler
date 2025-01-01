package core.preprocessors;

import api.preprocessors.InstructionPreprocessor;
import api.OperationTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelPreprocessor implements InstructionPreprocessor {
    private final OperationTable operationTable;

    public LabelPreprocessor(OperationTable operationTable) {
        this.operationTable = operationTable;
    }

    @Override
    public List<String> preprocessProgram(List<String> program) throws IllegalArgumentException {
        List<String> processedProgram = new ArrayList<>();

        Map<String, Integer> labelMap = new HashMap<>();
        for (int line = 0; line < program.size(); ++line) {
            String programLine = program.get(line).trim();
            if (programLine.isBlank() || programLine.startsWith("//")) continue;
            if (programLine.matches("^[a-zA-Z0-9]+:.*$")) {
                String label = programLine.split(":")[0];
                if (operationTable.getOperationCode(label).isPresent()) throw new IllegalArgumentException(
                    "Can't name label " + label + " as it is an assembler operation"
                );
                labelMap.put(label, line);
            }
            processedProgram.add(programLine.replaceAll("^[a-zA-Z0-9]+: *", ""));
        }

        processedProgram = processedProgram.stream().map(line -> line.isEmpty() ? "nop" : line).toList();

        return processedProgram.stream().map(
            line -> {
                for (String label : labelMap.keySet()) {
                    line = line.replaceAll(label, String.valueOf(labelMap.get(label)));
                }
                return line;
            }
        ).toList();
    }
}

package core.preprocessors;

import api.preprocessors.CompoundPreprocessor;
import api.preprocessors.InstructionPreprocessor;
import api.OperationTable;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AssemblyPreprocessor implements CompoundPreprocessor {
    private final PriorityQueue<PreprocessorEntry> preprocessors = new PriorityQueue<>(
        Comparator.comparingInt(prep -> prep.priority)
    );

    public AssemblyPreprocessor(OperationTable operationTable) {
        registerPreprocessor(new LabelPreprocessor(operationTable), -1);
        registerPreprocessor(new UserAliasPreprocessor(), 0);
    }

    @Override
    public List<String> preprocessProgram(List<String> program) {
        for (PreprocessorEntry entry : preprocessors) program = entry.preprocessor.preprocessProgram(program);
        return program;
    }

    @Override
    public void registerPreprocessor(InstructionPreprocessor preprocessor, int priority) {
        preprocessors.add(new PreprocessorEntry(preprocessor, -priority));
    }

    private record PreprocessorEntry(InstructionPreprocessor preprocessor, int priority) {
    }
}
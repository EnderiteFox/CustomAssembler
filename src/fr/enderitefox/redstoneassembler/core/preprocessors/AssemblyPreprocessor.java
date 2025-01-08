package fr.enderitefox.redstoneassembler.core.preprocessors;

import fr.enderitefox.redstoneassembler.api.preprocessors.CompoundPreprocessor;
import fr.enderitefox.redstoneassembler.api.preprocessors.InstructionPreprocessor;
import fr.enderitefox.redstoneassembler.api.OperationTable;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>
 *     A compound preprocessor composed of multiple default preprocessors
 * </p>
 * <p>
 *     The label preprocessor has a priority of 10, while the user label preprocessor has a priority of -10
 * </p>
 */
public class AssemblyPreprocessor implements CompoundPreprocessor {
    private final PriorityQueue<PreprocessorEntry> preprocessors = new PriorityQueue<>(
        Comparator.comparingInt(prep -> prep.priority)
    );

    public AssemblyPreprocessor(OperationTable operationTable) {
        registerPreprocessor(new LabelPreprocessor(operationTable), 10);
        registerPreprocessor(new UserAliasPreprocessor(), -10);
    }

    @Override
    public List<String> preprocessProgram(List<String> program) throws IllegalArgumentException {
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

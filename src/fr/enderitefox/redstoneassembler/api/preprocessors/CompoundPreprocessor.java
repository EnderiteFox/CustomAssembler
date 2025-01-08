package fr.enderitefox.redstoneassembler.api.preprocessors;

/**
 * A Preprocessor composed of multiple other Preprocessors, which are applied in the order of their priority, from the highest to the lowest
 * Internal preprocessors, like the {@link fr.enderitefox.redstoneassembler.core.preprocessors.LabelPreprocessor} have a priority of 10 to -10
 */
public interface CompoundPreprocessor extends InstructionPreprocessor {
    void registerPreprocessor(InstructionPreprocessor preprocessor, int priority);
}

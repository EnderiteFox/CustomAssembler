package api.preprocessors;

/**
 * A Preprocessor composed of multiple other Preprocessors, which are applied in the order of their priority, from the highest to the lowest
 * Internal preprocessors, like the {@link core.preprocessors.LabelPreprocessor} have a priority of 0 to -10,
 * so positive priorities are applied before internal preprocessors, while priorities less than -10 are applied after
 */
public interface CompoundPreprocessor extends InstructionPreprocessor {
    void registerPreprocessor(InstructionPreprocessor preprocessor, int priority);
}

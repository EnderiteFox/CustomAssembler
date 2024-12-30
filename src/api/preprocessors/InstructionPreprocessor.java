package api.preprocessors;

import java.util.List;

public interface InstructionPreprocessor {
    List<String> preprocessProgram(List<String> program);
}

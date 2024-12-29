package api;

import java.io.File;
import java.util.List;

public interface ProgramReader {
    List<String> readProgram(File file);

    default List<String> readProgram(String filePath) {
        return readProgram(new File(filePath));
    }
}

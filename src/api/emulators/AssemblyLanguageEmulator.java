package api.emulators;

import java.util.List;

public interface AssemblyLanguageEmulator<T> {
    T emulateProgram(List<Short> program);
}

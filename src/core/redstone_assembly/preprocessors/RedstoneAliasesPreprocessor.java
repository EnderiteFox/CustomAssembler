package core.redstone_assembly.preprocessors;

import api.preprocessors.InstructionPreprocessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedstoneAliasesPreprocessor implements InstructionPreprocessor {
    private final Map<String, String> aliases = new HashMap<>();

    public RedstoneAliasesPreprocessor() {
        // set <reg> <NUMBER> => addi <reg> <NUMBER>
        aliases.put(
            "^set r([0-9]|1[0-5]) ([0-9]+|0b[01]{1,8}|0x[0-9a-fA-F]{1,2})$",
            "addi r$1 $2"
        );

        // lsh <reg> => add <reg> <reg> <reg>
        aliases.put(
            "^lsh r([0-9]|1[0-5])$",
            "add r$1 r$1 r$1"
        );
    }

    @Override
    public List<String> preprocessProgram(List<String> program) {
        return program.stream().map(
            line -> {
                if (line.startsWith("//")) return line;
                for (String reg : aliases.keySet()) line = line.replaceAll(reg, aliases.get(reg));
                return line;
            }
        ).toList();
    }
}

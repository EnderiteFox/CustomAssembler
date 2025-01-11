package fr.enderitefox.redstoneassembler.core.redstone_assembly.preprocessors;

import fr.enderitefox.redstoneassembler.api.preprocessors.InstructionPreprocessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedstoneAliasesPreprocessor implements InstructionPreprocessor {
    private final Map<String, String> aliases = new HashMap<>();

    public RedstoneAliasesPreprocessor() {
        // reset <reg> => and <reg> r0 r0
        aliases.put(
            "^reset r([0-9]|1[0-5])$",
            "and r$1 r0 r0"
        );

        // lsh <reg> => add <reg> <reg> <reg>
        aliases.put(
            "^lsh r([0-9]|1[0-5])$",
            "add r$1 r$1 r$1"
        );

        // <reg>++ => addi <reg> 1
        aliases.put(
            "^r([0-9]|1[0-5])\\+\\+$",
            "addi r$1 1"
        );

        // <reg>-- => addi <reg> -1
        aliases.put(
            "^r([0-9]|1[0-5])--$",
            "addi r$1 -1"
        );
    }

    @Override
    public List<String> preprocessProgram(List<String> program) throws IllegalArgumentException {
        return program.stream().map(
            line -> {
                if (line.startsWith("//")) return line;
                for (String reg : aliases.keySet()) line = line.replaceAll(reg, aliases.get(reg));
                return line;
            }
        ).toList();
    }
}

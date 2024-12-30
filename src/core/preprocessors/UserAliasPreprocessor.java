package core.preprocessors;

import api.preprocessors.InstructionPreprocessor;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAliasPreprocessor implements InstructionPreprocessor {
    @Override
    public List<String> preprocessProgram(List<String> program) {
        List<String> processedProgram = new ArrayList<>();
        Map<String, String> aliases = new HashMap<>();

        for (String line : program) {
            String newLine = line.trim();
            if (!newLine.matches("^ALIAS /.*/ TO /.*/$")) {
                processedProgram.add(line);
                continue;
            }

            newLine = newLine.substring(7);
            newLine = newLine.substring(0, newLine.length() - 1);
            String[] regs = newLine.split("/ TO /");
            if (regs.length != 2) {
                System.err.println("Ignored malformed alias line: " + line);
                continue;
            }

            aliases.put(regs[0], regs[1]);
        }

        return processedProgram.stream().map(line -> transformLine(line, aliases)).toList();
    }

    private String transformLine(String line, Map<String, String> aliases) {
        String newLine = line;
        for (String reg : aliases.keySet()) newLine = newLine.replaceAll(reg, aliases.get(reg));
        if (!newLine.equals(line)) newLine = transformLine(newLine, aliases);
        return newLine;
    }
}

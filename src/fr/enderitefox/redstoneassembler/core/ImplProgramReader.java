package fr.enderitefox.redstoneassembler.core;

import fr.enderitefox.redstoneassembler.api.ProgramReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImplProgramReader implements ProgramReader {
    @Override
    public List<String> readProgram(File file) {
        List<String> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines().forEach(instructions::add);
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            return null;
        }

        return instructions;
    }
}

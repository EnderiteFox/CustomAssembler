package core;

import api.Assembler;
import api.emulators.AssemblyLanguageEmulator;
import api.OperationTable;
import api.ProgramReader;
import api.preprocessors.CompoundPreprocessor;
import core.preprocessors.AssemblyPreprocessor;
import core.redstone_assembly.RedstoneAssembler;
import core.redstone_assembly.RedstoneOperationTable;
import core.redstone_assembly.emulator.RedstoneAssemblyEmulator;
import core.redstone_assembly.emulator.RedstoneAssemblyResult;
import core.redstone_assembly.preprocessors.RedstoneAliasesPreprocessor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final ProgramReader programReader = new ImplProgramReader();
        List<String> program = programReader.readProgram(args[0]);

        System.out.println("Pure program");
        program.forEach(System.out::println);
        System.out.println();

        OperationTable operationTable = new RedstoneOperationTable();
        CompoundPreprocessor preprocessor = new AssemblyPreprocessor(operationTable);
        preprocessor.registerPreprocessor(new RedstoneAliasesPreprocessor(), 0);
        try {
            program = preprocessor.preprocessProgram(program);
        }
        catch (IllegalArgumentException e) {
            System.err.println("Error while preprocessing program");
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Preprocessed program");
        program.forEach(System.out::println);
        System.out.println();

        Assembler assembler = new RedstoneAssembler(operationTable);
        List<Short> assembledProgram;
        try {
            assembledProgram = assembler.readProgram(program);
            System.out.println("Machine code");
            assembler.printAssemblyProgram(assembledProgram);
        }
        catch (InternalError e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println();
        System.out.println("Emulating program");
        AssemblyLanguageEmulator<RedstoneAssemblyResult> emulator = new RedstoneAssemblyEmulator();
        RedstoneAssemblyResult result;

        try {
            result = emulator.emulateProgram(assembledProgram);
        }
        catch (InternalError e) {
            System.err.println("Error while running program:");
            System.err.println(e.getMessage());
            return;
        }

        System.out.println(result.toString());
    }
}

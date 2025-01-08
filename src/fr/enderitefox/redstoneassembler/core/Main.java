package fr.enderitefox.redstoneassembler.core;

import fr.enderitefox.redstoneassembler.api.Assembler;
import fr.enderitefox.redstoneassembler.api.emulators.AssemblyLanguageEmulator;
import fr.enderitefox.redstoneassembler.api.OperationTable;
import fr.enderitefox.redstoneassembler.api.ProgramReader;
import fr.enderitefox.redstoneassembler.api.preprocessors.CompoundPreprocessor;
import fr.enderitefox.redstoneassembler.core.preprocessors.AssemblyPreprocessor;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.RedstoneAssembler;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.RedstoneOperationTable;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.RedstoneAssemblyEmulator;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.emulator.RedstoneAssemblyResult;
import fr.enderitefox.redstoneassembler.core.redstone_assembly.preprocessors.RedstoneAliasesPreprocessor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Missing file argument!");
            return;
        }

        final ProgramReader programReader = new ImplProgramReader();
        List<String> program;
        try {
            program = programReader.readProgram(args[0]);
        }
        catch (InternalError e) {
            System.err.println("Internal error while reading program");
            System.err.println(e.getMessage());
            return;
        }
        catch (IllegalArgumentException e) {
            System.err.println("Error while reading program");
            System.err.println(e.getMessage());
            return;
        }
        if (program == null) return;

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

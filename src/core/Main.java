package core;

import api.Assembler;
import api.OperationTable;
import api.ProgramReader;
import api.preprocessors.CompoundPreprocessor;
import core.preprocessors.AssemblyPreprocessor;
import core.redstone_assembly.RedstoneAssembler;
import core.redstone_assembly.RedstoneOperationTable;
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
        preprocessor.registerPreprocessor(new RedstoneAliasesPreprocessor(), 1);
        program = preprocessor.preprocessProgram(program);

        System.out.println("Preprocessed program");
        program.forEach(System.out::println);
        System.out.println();

        Assembler assembler = new RedstoneAssembler(operationTable);
        List<Short> assembledProgram;
        try {
            assembledProgram = assembler.readProgram(program);
            assembler.printAssemblyProgram(assembledProgram);
        }
        catch (InternalError e) {
            System.err.println(e.getMessage());
        }
    }
}

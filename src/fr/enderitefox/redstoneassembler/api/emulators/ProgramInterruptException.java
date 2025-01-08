package fr.enderitefox.redstoneassembler.api.emulators;

/**
 * <p>Thrown by language emulators when the program should be interrupted
 * <p>Usually this is thrown by HALT instructions
 */
public class ProgramInterruptException extends RuntimeException {
}

package modulo.logic.commands.exceptions;

import modulo.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class CommandException extends Exception {
    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message}.
     *
     * @param message Error message.
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     *
     * @param message Error message.
     * @param cause   Cause for error.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

package modulo.logic;

import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.parser.exceptions.ParseException;

/**
 * API of the Stateful Logic component. This is a component whose behaviour changes based on the given state.
 */
public interface StatefulLogic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns a boolean denoting whether the {@code StatefulLogic} still has state.
     *
     * @return Boolean representing whether the {@code StatefulLogic} still has state.
     */
    boolean hasState();

    /**
     * Sets the state of the {@code StatefulLogic} using a specific {@code CommandResult}.
     *
     * @param commandResult CommandResult to load state from.
     */
    void setStateWithCommandResult(CommandResult commandResult);
}

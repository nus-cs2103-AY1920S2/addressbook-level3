package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.ParseException;

public interface AppCommand {
    /**
     * Command interface
     *
     * @param   arguments           Arguments for command
     * @return  CommandResult
     * @throws  ParseException      Invalid argument case
     */
    public AppCommandResult execute(String arguments) throws ParseException;
}

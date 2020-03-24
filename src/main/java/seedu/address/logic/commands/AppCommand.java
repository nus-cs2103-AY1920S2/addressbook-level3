package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;

public interface AppCommand {
    public AppCommand validate(String arguments) throws ParseException;

    /**
     * Command interface
     *
     * @param   dao                 Arguments for command
     * @return  CommandResult
     * @throws  ParseException      Invalid argument case
     */
    public AppCommandResult execute(AppStorage dao);
}

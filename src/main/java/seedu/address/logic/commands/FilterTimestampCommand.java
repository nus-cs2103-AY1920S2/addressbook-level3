package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.ParseException;

public class FilterTimestampCommand implements AppCommand {
    public static final String COMMAND_WORD = "from";

    @Override
    public AppCommandResult execute(String arguments) throws ParseException {
        return new AppCommandResult("Fake result", false);
    }
}

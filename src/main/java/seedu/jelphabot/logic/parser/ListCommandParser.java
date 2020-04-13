package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jelphabot.logic.commands.ListCommand;
import seedu.jelphabot.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        String input = args.trim();
        if (input.isEmpty()) {
            return new ListCommand();
        }
        switch (input) {
        case ListCommand.DATE_GROUPING:
            return new ListCommand("date");
        case ListCommand.MODULE_GROUPING:
            return new ListCommand("module");
        default:
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}

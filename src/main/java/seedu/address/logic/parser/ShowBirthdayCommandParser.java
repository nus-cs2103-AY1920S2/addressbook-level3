package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ShowBirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input for the new ShowBirthdayCommand.
 */
public class ShowBirthdayCommandParser implements Parser<ShowBirthdayCommand> {
    @Override
    public ShowBirthdayCommand parse(String userInput) throws ParseException {
        if (!userInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowBirthdayCommand.MESSAGE_USAGE));
        }

        return new ShowBirthdayCommand();
    }
}

package seedu.address.logic.parser.facilitator;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.facilitator.FacilFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.facilitator.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FacilFindCommand object.
 */
public class FacilFindCommandParser implements Parser<FacilFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FacilFindCommand
     * and returns a FacilFindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FacilFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacilFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FacilFindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}

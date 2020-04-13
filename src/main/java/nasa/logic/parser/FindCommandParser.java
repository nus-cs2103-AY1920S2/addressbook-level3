package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import nasa.logic.commands.FindCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.ActivityContainsKeyWordsPredicate;

//@@author kester-ng
/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new ActivityContainsKeyWordsPredicate(Arrays.asList(nameKeywords)));
    }
}

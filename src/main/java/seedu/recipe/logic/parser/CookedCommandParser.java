package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.CookedCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CookedCommand object
 */
public class CookedCommandParser implements Parser<CookedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CookedCommand
     * and returns a CookedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CookedCommand parse(String args) throws ParseException {
        try {
            Index[] index = ParserUtil.parseMultipleIndex(args);
            return new CookedCommand(index);
        } catch (ParseException exception) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CookedCommand.MESSAGE_USAGE), exception);
        }
    }

}

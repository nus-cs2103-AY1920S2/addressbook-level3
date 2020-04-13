package seedu.nova.logic.parser.abparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.abcommands.AbDeleteCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AbDeleteCommand object
 */
public class AbDeleteCommandParser implements Parser<AbDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AbDeleteCommand
     * and returns a AbDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AbDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (argMultimap.getValue(CliSyntax.PREFIX_INDEX).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_INDEX).get());
            return new AbDeleteCommand(index);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbDeleteCommand.MESSAGE_USAGE));
        }
    }

}

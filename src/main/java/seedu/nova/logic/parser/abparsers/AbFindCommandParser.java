package seedu.nova.logic.parser.abparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;

import seedu.nova.logic.commands.abcommands.AbFindCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.person.Name;
import seedu.nova.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new AbFindCommand object
 */
public class AbFindCommandParser implements Parser<AbFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AbFindCommand
     * and returns a AbFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AbFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Name name;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            seedu.nova.logic.commands.abcommands.AbFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = name.toString().split("\\s+");
        return new AbFindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}

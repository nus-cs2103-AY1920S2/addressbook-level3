package seedu.address.logic.parser.tutorials;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tutorials.CopyTutorialEmailsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input commands and creates a new CopyTutorialEmailsCommand object
 */
public class CopyTutorialEmailsCommandParser implements Parser<CopyTutorialEmailsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyTutorialEmailsCommand
     * and returns a CopyTutorialEmailsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyTutorialEmailsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CopyTutorialEmailsCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesUnique(argMultimap, PREFIX_TUTORIAL_INDEX)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    CopyTutorialEmailsCommand.MESSAGE_USAGE));
        }

        Index tutorialIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TUTORIAL_INDEX).get());

        return new CopyTutorialEmailsCommand(tutorialIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}

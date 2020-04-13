package seedu.address.logic.parser.tutorials;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tutorials.DeleteTutorialStudentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTutorialStudentCommand object
 */
public class DeleteTutorialStudentCommandParser implements Parser<DeleteTutorialStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTutorialStudentCommand
     * and returns a DeleteTutorialStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTutorialStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_INDEX, PREFIX_STUDENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_INDEX, PREFIX_STUDENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTutorialStudentCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesUnique(argMultimap, PREFIX_TUTORIAL_INDEX, PREFIX_STUDENT)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    DeleteTutorialStudentCommand.MESSAGE_USAGE));
        }

        Index tutorialIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TUTORIAL_INDEX).get());
        Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());

        return new DeleteTutorialStudentCommand(tutorialIndex, studentIndex);
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

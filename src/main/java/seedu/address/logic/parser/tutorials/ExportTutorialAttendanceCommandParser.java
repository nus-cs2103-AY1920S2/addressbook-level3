package seedu.address.logic.parser.tutorials;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CSV_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import java.nio.file.Path;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tutorials.ExportTutorialAttendanceCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input commands and creates a new CopyTutorialEmailsCommand object
 */
public class ExportTutorialAttendanceCommandParser implements Parser<ExportTutorialAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportTutorialAttendanceCommand
     * and returns a ExportTutorialAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportTutorialAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_INDEX, PREFIX_CSV_FILEPATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_INDEX, PREFIX_CSV_FILEPATH)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportTutorialAttendanceCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesUnique(argMultimap, PREFIX_TUTORIAL_INDEX, PREFIX_CSV_FILEPATH)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    ExportTutorialAttendanceCommand.MESSAGE_USAGE));
        }

        Index tutorialIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TUTORIAL_INDEX).get());
        Path csvFilePath = ParserUtil.parseCsvFilePath(argMultimap.getValue(PREFIX_CSV_FILEPATH).get());

        return new ExportTutorialAttendanceCommand(tutorialIndex, csvFilePath);
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

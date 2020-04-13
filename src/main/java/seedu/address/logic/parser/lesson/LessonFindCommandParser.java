package seedu.address.logic.parser.lesson;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT;

import java.time.DayOfWeek;
import java.util.stream.Stream;

import seedu.address.logic.commands.lesson.LessonFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LessonFindNextCommand object or LessonNextCommand
 */
public class LessonFindCommandParser implements Parser<LessonFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LessonFindCommand
     * and returns a LessonFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LessonFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AT, PREFIX_NEXT);
        if (!arePrefixesPresent(argumentMultimap, PREFIX_AT) && !arePrefixesPresent(argumentMultimap, PREFIX_NEXT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonFindCommand.MESSAGE_USAGE));
        } else if (arePrefixesPresent(argumentMultimap, PREFIX_AT)
                && arePrefixesPresent(argumentMultimap, PREFIX_NEXT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonFindCommand.MESSAGE_USAGE));
        }

        if (argumentMultimap.numOfValuesPresent(PREFIX_AT) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_AT));
        }
        if (argumentMultimap.numOfValuesPresent(PREFIX_NEXT) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_NEXT));
        }

        DayOfWeek day = null;
        if (arePrefixesPresent(argumentMultimap, PREFIX_AT)) {
            day = ParserUtil.parseDay(argumentMultimap.getValue(PREFIX_AT));
        }

        return new LessonFindCommand(day);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix) != null);
    }
}

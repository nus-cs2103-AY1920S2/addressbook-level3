package seedu.address.logic.parser.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_SEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_YEAR;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.timetable.TimeTableRemoveCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.time.Semester;
import seedu.address.model.time.SemesterYear;
import seedu.address.model.time.StudentSemester;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class TimeTableRemoveCommandParser implements Parser<TimeTableRemoveCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TimeTableRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // NOTE: the concatenation " " is a workaround for `ArgumentTokenizer` treating the first argument as the
        // preamble
        // TODO: use ArgumentTokenizer for all subcommands
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + args, PREFIX_STUDENT_SEM,
            PREFIX_STUDENT_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT_SEM, PREFIX_STUDENT_YEAR)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableRemoveCommand.MESSAGE_USAGE));
        }

        Index index;
        Semester sem;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_YEAR).get());
            sem = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_STUDENT_SEM).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimeTableRemoveCommand.MESSAGE_USAGE), pe);
        }

        SemesterYear semesterYear = new SemesterYear(sem, 0); // TODO: input academic year
        StudentSemester studentSemester = new StudentSemester(semesterYear, index.getOneBased());
        return new TimeTableRemoveCommand(studentSemester);
    }
}

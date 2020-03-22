package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.model.profile.Profile.getModules;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.profile.course.Course;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.Module;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEMESTER, PREFIX_COURSE_NAME);

        // Get Semester
        if (arePrefixesPresent(argMultimap, PREFIX_SEMESTER)) {
            String semester = argMultimap.getValue(PREFIX_SEMESTER).get();
            if (!ParserUtil.isInteger(semester)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
            }
            int intSemester = Integer.parseInt(semester);
            ArrayList<Module> modulesList = getModules(intSemester);

            return new ShowCommand(modulesList);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)) {
            String name = argMultimap.getValue(PREFIX_COURSE_NAME).get();
            Course course = CourseManager.getCourse(new CourseName(name));
            return new ShowCommand(course);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}


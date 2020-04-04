package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPEC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.ModuleCode;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;

/**
 * Parses input arguments and creates a new NewCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns a EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException, DateTimeException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COURSE_NAME, PREFIX_CURRENT_SEMESTER, PREFIX_SPEC,
                        PREFIX_MODULE, PREFIX_SEMESTER, PREFIX_GRADE, PREFIX_TASK, PREFIX_NEW_TASK, PREFIX_DEADLINE);

        if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) { // EDIT MODULE
            if (!arePrefixesPresent(argMultimap, PREFIX_SEMESTER) && !arePrefixesPresent(argMultimap, PREFIX_GRADE)
                    && !arePrefixesPresent(argMultimap, PREFIX_TASK)
                    && !arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }

            String moduleCodeString = argMultimap.getValue(PREFIX_MODULE).get();
            moduleCodeString = moduleCodeString.trim();
            moduleCodeString = moduleCodeString.toUpperCase();
            ModuleCode moduleCode = new ModuleCode(moduleCodeString);

            int intSemester = 0;
            String grade = null;
            String oldTask = null;
            String newTask = null;
            String newDeadline = null;

            if (arePrefixesPresent(argMultimap, PREFIX_SEMESTER)) {
                String semester = argMultimap.getValue(PREFIX_SEMESTER).get();
                if (!ParserUtil.isInteger(semester)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                intSemester = Integer.parseInt(semester);
            }

            if (arePrefixesPresent(argMultimap, PREFIX_GRADE)) {
                grade = argMultimap.getValue(PREFIX_GRADE).get();
            }

            // Reject when deadline is given but task name not given
            if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE)
                    && !arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                throw new ParseException("Error: Please provide the name of the task you are trying to edit.");
            }

            if (arePrefixesPresent(argMultimap, PREFIX_TASK)) {
                oldTask = argMultimap.getValue(PREFIX_TASK).get();
                if (!arePrefixesPresent(argMultimap, PREFIX_DEADLINE)
                        && !arePrefixesPresent(argMultimap, PREFIX_NEW_TASK)) {
                    throw new ParseException("Error: Please specify a new task name or deadline to be edited.");
                }
                if (arePrefixesPresent(argMultimap, PREFIX_NEW_TASK)) {
                    newTask = argMultimap.getValue(PREFIX_NEW_TASK).get().trim();
                }
                if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
                    newDeadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
                }
            }
            return new EditCommand(moduleCode, intSemester, grade, oldTask, newTask, newDeadline);
        } else { // EDIT PROFILE

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            } else if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)
                    && !arePrefixesPresent(argMultimap, PREFIX_CURRENT_SEMESTER)
                    && !arePrefixesPresent(argMultimap, PREFIX_SPEC)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }

            Name name = null;
            CourseName courseName = null;
            int currentSemester = 0;
            String specialisation = null;

            if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            }
            if (arePrefixesPresent(argMultimap, PREFIX_COURSE_NAME)) {
                courseName = ParserUtil.parseCourseName(argMultimap.getValue(PREFIX_COURSE_NAME).get());
            }
            if (arePrefixesPresent(argMultimap, PREFIX_CURRENT_SEMESTER)) {
                currentSemester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_CURRENT_SEMESTER).get());
            }
            if (arePrefixesPresent(argMultimap, PREFIX_SPEC)) {
                specialisation = argMultimap.getValue(PREFIX_SPEC).get();
            }
            return new EditCommand(name, courseName, currentSemester, specialisation);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

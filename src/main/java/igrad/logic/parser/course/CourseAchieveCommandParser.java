package igrad.logic.parser.course;

import static igrad.logic.commands.course.CourseAchieveCommand.MESSAGE_ACHIEVED_CAP_NOT_CALCULATED;
import static igrad.logic.commands.course.CourseAchieveCommand.MESSAGE_COURSE_ACHIEVE_HELP;
import static igrad.logic.commands.course.CourseAchieveCommand.MESSAGE_SEMS_LEFT_NEEDED;
import static igrad.logic.parser.CliSyntax.PREFIX_CAP;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import igrad.commons.core.Messages;
import igrad.logic.commands.course.CourseAchieveCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Cap;

/**
 * Parses input arguments and creates a new ModuleDeleteCommand object.
 */
public class CourseAchieveCommandParser implements Parser<CourseAchieveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleDeleteCommand
     * and returns a ModuleDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAchieveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CAP, PREFIX_SEMESTER);

        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_COURSE_ACHIEVE_HELP));
        }

        if (argMultimap.getValue(PREFIX_CAP).isEmpty()) {
            throw new ParseException(MESSAGE_ACHIEVED_CAP_NOT_CALCULATED);
        }

        if (argMultimap.getValue(PREFIX_SEMESTER).isEmpty()) {
            throw new ParseException(MESSAGE_SEMS_LEFT_NEEDED);
        }

        Cap cap = new Cap(argMultimap.getValue(PREFIX_CAP).get());
        int semsLeft = Integer.parseInt(argMultimap.getValue(PREFIX_SEMESTER).get());

        return new CourseAchieveCommand(cap, semsLeft);
    }

}

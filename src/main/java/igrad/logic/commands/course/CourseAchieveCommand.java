package igrad.logic.commands.course;

import static igrad.logic.parser.CliSyntax.PREFIX_CAP;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;

/**
 * Adds a course to the application (there can only be one such course).
 */
public class CourseAchieveCommand extends CourseCommand {
    public static final String COURSE_ACHIEVE_COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "achieve";

    public static final String MESSAGE_COURSE_ACHIEVE_SUCCESS = "You need to maintain an average CAP (per sem) "
        + "of: %1$s";
    public static final String MESSAGE_ACHIEVED_CAP_NOT_CALCULATED = "Please enter desired CAP";
    public static final String MESSAGE_SEMS_LEFT_NEEDED = "Please enter semesters left";
    public static final String MESSAGE_COURSE_ACHIEVE_DETAILS = COURSE_ACHIEVE_COMMAND_WORD + ": Calculates average "
        + "CAP needed per sem"
        + " to achieve desired CAP\n";
    public static final String MESSAGE_COURSE_ACHIEVE_USAGE = "Parameter(s): " + PREFIX_CAP + "DESIRED CAP "
            + PREFIX_SEMESTER + "SEMESTERS LEFT";
    public static final String MESSAGE_COURSE_ACHIEVE_HELP = MESSAGE_COURSE_ACHIEVE_DETAILS
        + MESSAGE_COURSE_ACHIEVE_USAGE;

    private final Cap capToAchieve;
    private final int semsLeft;

    public CourseAchieveCommand(Cap capToAchieve, int semsLeft) {
        this.capToAchieve = capToAchieve;
        this.semsLeft = semsLeft;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Cap estimatedCap = model.computeEstimatedCap(capToAchieve, semsLeft);

        return new CommandResult(String.format(MESSAGE_COURSE_ACHIEVE_SUCCESS, estimatedCap));
    }
}

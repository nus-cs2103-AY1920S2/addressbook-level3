package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENT_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOCUS_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.course.CourseName;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Displays details requested by user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": The following items can be shown:\n"
            + "Course, with " + PREFIX_COURSE_NAME + "COURSE\n"
            + "Course Focus Area, with " + PREFIX_FOCUS_AREA + "FOCUS_AREA\n"
            + "Module, with " + PREFIX_MODULE + "MODULE_CODE\n"
            + "Modules taken in a semester, with " + PREFIX_CURRENT_SEMESTER + "SEMESTER_NUMBER\n";

    public static final String MESSAGE_SUCCESS_COURSE = "Course requirements for this course are: \n%1$s";
    public static final String MESSAGE_SUCCESS_FOCUS_AREA = "Modules in this focus area are:\n%1$s";
    public static final String MESSAGE_SUCCESS_MODULE = "The details for this module are:\n%1$s";
    public static final String MESSAGE_SUCCESS_MODULE_LIST = "All modules taken in this semester are shown: \n%1$s";

    private final Object itemParsed;
    private Object toShow;
    /**
     * Creates a ShowCommand to show the specified object
     * @param itemParsed - Can be any object from this list: (Course,
     *               CourseFocusArea, Module, List of Modules in a semester)
     */
    public ShowCommand(Object itemParsed) {
        requireNonNull(itemParsed);
        this.itemParsed = itemParsed;
    }

    @Override
    public CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                 ModuleManager moduleManager) throws CommandException {
        requireNonNull(profileManager);
        requireNonNull(courseManager);
        requireNonNull(moduleManager);

        String message = "";
        try {
            if (itemParsed instanceof CourseName) {

                message = MESSAGE_SUCCESS_COURSE;
                CourseName courseName = (CourseName) itemParsed;
                toShow = courseManager.getCourse(courseName);

            } else if (itemParsed instanceof Integer) {

                message = MESSAGE_SUCCESS_MODULE_LIST;
                Integer semester = (Integer) itemParsed;
                toShow = profileManager.getFirstProfile().getModules(semester);

            } else if (itemParsed instanceof ModuleCode) {

                message = MESSAGE_SUCCESS_MODULE;
                ModuleCode moduleCode = (ModuleCode) itemParsed;
                toShow = moduleManager.getModule(moduleCode);

            } else if (itemParsed instanceof String) {

                message = MESSAGE_SUCCESS_FOCUS_AREA;
                String focusArea = (String) itemParsed;
                toShow = courseManager.getCourseFocusArea(focusArea);

            }
            return new CommandResult(String.format(message, toShow));

        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && toShow.equals(((ShowCommand) other).toShow));
    }
}

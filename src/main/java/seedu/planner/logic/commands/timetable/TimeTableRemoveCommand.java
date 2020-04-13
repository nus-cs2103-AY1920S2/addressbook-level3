package seedu.planner.logic.commands.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_STUDENT_SEM;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_STUDENT_YEAR;

import java.util.Objects;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.commands.CommandResult;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.time.StudentSemester;


//@@author thetruevincentchow

/**
 * Removes a timetable from the currently selected student's timetable list.
 */
public class TimeTableRemoveCommand extends TimeTableCommand {
    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = getQualifiedCommand(COMMAND_WORD)
            + ": Removes a timetable identified by the given semester from the active student.\n"
            + "Parameters: "
            + PREFIX_STUDENT_YEAR + "YEAR "
            + PREFIX_STUDENT_SEM + "SEMESTER\n"
            + "Examples: \n"
            + "    " + getQualifiedCommand(COMMAND_WORD) + " year/1 sem/1\n"
            + "    " + getQualifiedCommand(COMMAND_WORD) + " year/2 sem/2\n"
            + "    " + getQualifiedCommand(COMMAND_WORD) + " year/3 sem/st1\n"
            + "    " + getQualifiedCommand(COMMAND_WORD) + " year/4 sem/st2\n";

    public static final String MESSAGE_REMOVE_TIMETABLE_SUCCESS = "Removed timetable from semester: %1$s";

    private final StudentSemester studentSemester;

    public TimeTableRemoveCommand(StudentSemester studentSemester) {
        requireAllNonNull(studentSemester);
        this.studentSemester = studentSemester;
    }

    /**
     * Generates a command execution success message for removing the timetable
     * with the given {@code semesterYear} from the currently selected student's list of timetables.
     */
    private String generateSuccessMessage(StudentSemester semesterYear) {
        return String.format(MESSAGE_REMOVE_TIMETABLE_SUCCESS, semesterYear);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if active student exists
        if (!model.hasActiveStudent()) {
            throw new CommandException(Messages.MESSAGE_NO_STUDENT_ACTIVE);
        }

        if (!model.hasSemester(studentSemester)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_SEMESTER, studentSemester));
        }

        model.removeSemesterTimeTable(studentSemester);

        return new CommandResult(generateSuccessMessage(studentSemester));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeTableRemoveCommand that = (TimeTableRemoveCommand) o;
        return studentSemester.equals(that.studentSemester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentSemester);
    }
}
//@@author

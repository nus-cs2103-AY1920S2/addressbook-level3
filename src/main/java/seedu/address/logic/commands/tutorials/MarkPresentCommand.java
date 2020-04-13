package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_STUDENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.student.Student;

/**
 * Marks a student or all students in a given tutorial in TAble as present.
 */
public class MarkPresentCommand extends Command {
    public static final String COMMAND_WORD = "markPresent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a student as present in given tutorial.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX (must be a positive integer) "
            + PREFIX_TUTORIAL_WEEK + "SEMESTER_WEEK (between 1 to 13 inclusive) "
            + PREFIX_STUDENT + "STUDENT_INDEX (set as \"all\" to mark all students in tutorial as present)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_INDEX + "1 "
            + PREFIX_TUTORIAL_WEEK + "10 "
            + PREFIX_STUDENT + "3";

    public static final String MESSAGE_SUCCESS = "Student %1$s in %2$s %3$s successfully marked as present for "
            + "week %4$s";
    public static final String MESSAGE_ALL_SUCCESS = "All students in %1$s %2$s successfully marked as present for "
            + "week %3$s";

    private final Index tutorialIndex;
    private final Index studentIndex;
    private final int week;
    private final boolean isMarkAll;

    /**
     * Creates a markPresentCommand to mark attendance of the specified student at {@code studentIndex}
     * to tutorial at {@code tutorialIndex} as present. If {@code isMarkAll} is true, then all students
     * in the tutorial will be marked as present.
     */
    public MarkPresentCommand(Index tutorialIndex, Index studentIndex, int week, Boolean isMarkAll) {
        this.tutorialIndex = tutorialIndex;
        this.studentIndex = studentIndex;
        this.isMarkAll = isMarkAll;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (tutorialIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }

        Tutorial tutorialToMark = lastShownList.get(tutorialIndex.getZeroBased());
        try {
            if (!isMarkAll) {
                Student studentToMark = tutorialToMark.getEnrolledStudents().get(studentIndex.getZeroBased());
                model.markPresent(tutorialToMark, studentToMark, week - 3);

                // Refresh the list to update the GUI attendance
                List<Tutorial> updatedShownList = model.getFilteredTutorialList();
                Tutorial updatedTutorial = updatedShownList.get(tutorialIndex.getZeroBased());

                return new CommandResult(
                    String.format(MESSAGE_SUCCESS,
                        studentToMark.getName().fullName,
                        tutorialToMark.getModCode(),
                        tutorialToMark.getTutorialName(),
                        week),
                    updatedTutorial,
                    week - 3);
            } else {
                for (Student student : tutorialToMark.getEnrolledStudents()) {
                    model.markPresent(tutorialToMark, student, week - 3);
                }

                // Refresh the list to update the GUI attendance
                List<Tutorial> updatedShownList = model.getFilteredTutorialList();
                Tutorial updatedTutorial = updatedShownList.get(tutorialIndex.getZeroBased());
                //System.out.println(updatedTutorial.getAttendanceWeek(week));

                return new CommandResult(String.format(MESSAGE_ALL_SUCCESS,
                        tutorialToMark.getModCode(), tutorialToMark.getTutorialName(), week), updatedTutorial,
                        week - 3);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_TUTORIAL_STUDENT_INDEX);
        }
    }
}

package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_STUDENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

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
 * Deletes an existing student from an existing Tutorial in TAble.
 * This does not delete the student from StudentTAble.
 */
public class DeleteTutorialStudentCommand extends Command {
    public static final String COMMAND_WORD = "deleteTutorialStudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an existing student from "
            + "an existing tutorial.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX (must be a positive integer) "
            + PREFIX_STUDENT + "STUDENT_INDEX (in tutorial)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_INDEX + "1 "
            + PREFIX_STUDENT + "2";

    public static final String MESSAGE_DELETE_TUTORIAL_STUDENT_SUCCESS = "Deleted tutorial student %1$s "
            + "from %2$s %3$s\n"
            + "Please use listAttendance command to view updated list of students.";

    private final Index tutorialIndex;
    private final Index studentIndex;

    /**
     * Creates a DeleteTutorialStudentCommand to delete the specified student at {@code studentIndex} (in the tutorial)
     * frm specified tutorial at {@code tutorialIndex}
     */
    public DeleteTutorialStudentCommand(Index tutorialIndex, Index studentIndex) {
        this.tutorialIndex = tutorialIndex;
        this.studentIndex = studentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (tutorialIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }

        Tutorial tutorialToDeleteFrom = lastShownList.get(tutorialIndex.getZeroBased());

        try {
            Student toDelete = tutorialToDeleteFrom.getEnrolledStudents().get(studentIndex.getZeroBased());
            model.deleteTutorialStudent(tutorialToDeleteFrom, toDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_TUTORIAL_STUDENT_SUCCESS, toDelete.getName().fullName,
                    tutorialToDeleteFrom.getModCode(), tutorialToDeleteFrom.getTutorialName()), false,
                    false, false, true, false);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_TUTORIAL_STUDENT_INDEX);
        }
    }
}

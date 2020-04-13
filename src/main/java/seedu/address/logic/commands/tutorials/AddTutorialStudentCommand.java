package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
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
 * Adds an existing student to an existing Tutorial in TAble.
 */
public class AddTutorialStudentCommand extends Command {
    public static final String COMMAND_WORD = "addTutorialStudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an existing student to an existing tutorial.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX (must be a positive integer) "
            + PREFIX_STUDENT + "STUDENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_INDEX + "1 "
            + PREFIX_STUDENT + "3";

    public static final String MESSAGE_SUCCESS = "New student %1$s added to %2$s %3$s.\n"
            + "Please use listAttendance command to view updated list of students.";
    public static final String MESSAGE_DUPLICATE_TUTORIAL_STUDENT = "This student already exists in this tutorial";

    private final Index tutorialIndex;
    private final Index studentIndex;

    /**
     * Creates an AddTutorialStudentCommand to add the specified student at {@code studentIndex}
     * to tutorial at {@code tutorialIndex}
     */
    public AddTutorialStudentCommand(Index tutorialIndex, Index studentIndex) {
        this.tutorialIndex = tutorialIndex;
        this.studentIndex = studentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownStudentList = model.getFilteredStudentList();
        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();

        if (studentIndex.getZeroBased() >= lastShownStudentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        if (tutorialIndex.getZeroBased() >= lastShownTutorialList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }

        Tutorial tutorialToAddTo = lastShownTutorialList.get(tutorialIndex.getZeroBased());
        Student studentToAdd = lastShownStudentList.get(studentIndex.getZeroBased());

        // check duplicate student
        if (model.hasTutorialStudent(tutorialToAddTo, studentToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL_STUDENT);
        }

        model.addTutorialStudent(tutorialToAddTo, studentToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToAdd.getName().fullName,
                tutorialToAddTo.getModCode().toString(), tutorialToAddTo.getTutorialName()), false,
                false, false, true, false);
    }
}

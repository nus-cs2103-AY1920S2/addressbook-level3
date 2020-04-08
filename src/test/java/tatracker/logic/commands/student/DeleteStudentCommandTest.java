package tatracker.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertDeleteStudentCommandSuccess;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_FIRST_STUDENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_NONEXISTENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_SECOND_STUDENT;
import static tatracker.testutil.TypicalTaTracker.getTypicalGroup;
import static tatracker.testutil.TypicalTaTracker.getTypicalModule;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithStudents;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalTaTrackerWithStudents(), new UserPrefs());

    @Test
    public void execute_validMatricUnfilteredList_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                getTypicalGroup().getIdentifier(), getTypicalModule().getIdentifier());

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertDeleteStudentCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Matric nonexistentMatric = MATRIC_NONEXISTENT;
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(nonexistentMatric,
                getTypicalGroup().getIdentifier(), getTypicalModule().getIdentifier());

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        //showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                getTypicalGroup().getIdentifier(), getTypicalModule().getIdentifier());

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        Model expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        //showNoStudent(expectedModel);

        assertDeleteStudentCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        //showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Matric nonexistentMatric = MATRIC_NONEXISTENT;
        // ensures that nonexistentMatric is still in bounds of ta-tracker list
        //assertTrue(outOfBoundIndex.getZeroBased() < model.getTaTracker().getCompleteStudentList().size());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(nonexistentMatric,
                getTypicalGroup().getIdentifier(), getTypicalModule().getIdentifier());

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                getTypicalGroup().getIdentifier(), getTypicalModule().getIdentifier());
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(MATRIC_SECOND_STUDENT,
                getTypicalGroup().getIdentifier(), getTypicalModule().getIdentifier());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                getTypicalGroup().getIdentifier(), getTypicalModule().getIdentifier());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}

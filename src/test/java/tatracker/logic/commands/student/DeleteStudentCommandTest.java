package tatracker.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertStudentCommandSuccess;
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
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalTaTrackerWithStudents(), new UserPrefs());
    private String typicalGroupCode = getTypicalGroup().getIdentifier();
    private String typicalModuleCode = getTypicalModule().getIdentifier();

    @Test
    public void execute_validMatricUnfilteredList_success() {
        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                typicalGroupCode, typicalModuleCode);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertStudentCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMatricUnfilteredList_throwsCommandException() {
        Matric nonexistentMatric = MATRIC_NONEXISTENT;
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(nonexistentMatric,
                typicalGroupCode, typicalModuleCode);

        assertCommandFailure(deleteStudentCommand, model, String.format(
                Messages.MESSAGE_INVALID_STUDENT_FORMAT,
                nonexistentMatric,
                typicalGroupCode,
                typicalModuleCode));
    }

    @Test
    public void execute_validMatricFilteredList_success() {
        //showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToDelete = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                typicalGroupCode, typicalModuleCode);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);

        Model expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        //showNoStudent(expectedModel);

        assertStudentCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMatricFilteredList_throwsCommandException() {
        //showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Matric nonexistentMatric = MATRIC_NONEXISTENT;
        // ensures that nonexistentMatric is still in bounds of ta-tracker list
        //assertTrue(outOfBoundIndex.getZeroBased() < model.getTaTracker().getCompleteStudentList().size());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(nonexistentMatric,
                typicalGroupCode, typicalModuleCode);

        assertCommandFailure(deleteStudentCommand, model, String.format(
                Messages.MESSAGE_INVALID_STUDENT_FORMAT,
                nonexistentMatric,
                typicalGroupCode,
                typicalModuleCode));
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                typicalGroupCode, typicalModuleCode);
        DeleteStudentCommand deleteCommandDifferentMatric = new DeleteStudentCommand(MATRIC_SECOND_STUDENT,
                typicalGroupCode, typicalModuleCode);
        DeleteStudentCommand deleteCommandDifferentGroup = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
               new Group("G05").getIdentifier(), typicalModuleCode);
        DeleteStudentCommand deleteCommandDifferentModule = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                typicalGroupCode, new Module("CS3242").getIdentifier());

        // same object -> returns true
        assertTrue(deleteCommand.equals(deleteCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                typicalGroupCode, typicalModuleCode);
        assertTrue(deleteCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteCommand.equals(1));

        // null -> returns false
        assertFalse(deleteCommand.equals(null));

        // different matric -> returns false
        assertFalse(deleteCommand.equals(deleteCommandDifferentMatric));

        // different group -> returns false
        assertFalse(deleteCommand.equals(deleteCommandDifferentGroup));

        // different module -> returns false
        assertFalse(deleteCommand.equals(deleteCommandDifferentModule));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}

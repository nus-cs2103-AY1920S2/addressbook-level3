//@@author fatin99

package tatracker.logic.commands.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_FIRST_STUDENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_NONEXISTENT;
import static tatracker.testutil.TypicalIndexes.MATRIC_SECOND_STUDENT;
import static tatracker.testutil.TypicalTaTracker.getTypicalGroup;
import static tatracker.testutil.TypicalTaTracker.getTypicalModule;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithStudents;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
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

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                studentToDelete.getName().fullName,
                studentToDelete.getMatric().value,
                typicalModuleCode,
                typicalGroupCode);

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel, Action.GOTO_STUDENT);
    }

    @Test
    public void execute_invalidMatricUnfilteredList_throwsCommandException() {
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(MATRIC_NONEXISTENT,
                typicalGroupCode, typicalModuleCode);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT);
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
        assertEquals(deleteCommand, deleteCommand);

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(MATRIC_FIRST_STUDENT,
                typicalGroupCode, typicalModuleCode);
        assertEquals(deleteCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteCommand);

        // null -> returns false
        assertNotNull(deleteCommand);

        // different matric -> returns false
        assertNotEquals(deleteCommand, deleteCommandDifferentMatric);

        // different group -> returns false
        assertNotEquals(deleteCommand, deleteCommandDifferentGroup);

        // different module -> returns false
        assertNotEquals(deleteCommand, deleteCommandDifferentModule);
    }
}

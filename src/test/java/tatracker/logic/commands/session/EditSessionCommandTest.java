package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.S1;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithSessions;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditStudentCommand.
 */
public class EditSessionCommandTest {
    private Model model = new ModelManager(getTypicalTaTrackerWithSessions(), new UserPrefs());

    /*@Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Session editedSession = new SessionBuilder().build();
        EditSessionDescriptor descriptor = new EditSessionDescriptorBuilder(editedSession).build();

        EditSessionCommand editSessionCommand = new EditSessionCommand(INDEX_FIRST_SESSION, descriptor);

        String expectedMessage = String.format(EditSessionCommand.MESSAGE_EDITED_SESSION_SUCCESS,
                editedSession.getMinimalDescription());

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.setSession(model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased()),
                editedSession);

        assertSessionCommandSuccess(editSessionCommand, model, expectedMessage, expectedModel);
    } */

    @Test
    public void equals() {
        final EditSessionCommand standardCommand = new EditSessionCommand(INDEX_FIRST_SESSION, S1);

        // same values -> returns true
        EditSessionDescriptor copyDescriptor = new EditSessionDescriptor(S1);
        EditSessionCommand commandWithSameValues = new EditSessionCommand(INDEX_FIRST_SESSION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

    }
}

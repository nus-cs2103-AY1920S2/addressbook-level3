//@@author Chuayijing

package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static tatracker.testutil.TypicalIndexes.INDEX_SECOND_SESSION;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithSessions;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.session.Session;

public class DeleteSessionCommandTest {

    private Model model = new ModelManager(getTypicalTaTrackerWithSessions(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Session sessionToDelete = model.getFilteredSessionList().get(INDEX_FIRST_SESSION.getZeroBased());
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(INDEX_FIRST_SESSION);

        String expectedMessage = String.format(DeleteSessionCommand.MESSAGE_DELETE_SESSION_SUCCESS,
                sessionToDelete.getMinimalDescription());

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.deleteSession(sessionToDelete);

        assertCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel, Action.GOTO_SESSION);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSessionList().size() + 1);
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(outOfBoundIndex);
        String expectedMessage = Messages.getInvalidCommandMessage(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        assertCommandFailure(deleteSessionCommand,
                 model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteSessionCommand deleteFirstCommand = new DeleteSessionCommand(INDEX_FIRST_SESSION);
        DeleteSessionCommand deleteSecondCommand = new DeleteSessionCommand(INDEX_SECOND_SESSION);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteSessionCommand deleteFirstCommandCopy = new DeleteSessionCommand(INDEX_FIRST_SESSION);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different sessions -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoSession(Model model) {
        model.updateFilteredSessionList(p -> false);

        assertTrue(model.getFilteredSessionList().isEmpty());
    }

}

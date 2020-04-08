package tatracker.logic.commands.session;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static tatracker.logic.commands.CommandTestUtil.assertDeleteSessionCommandSuccess;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithSessions;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_SESSION;
import static tatracker.testutil.TypicalIndexes.INDEX_SECOND_SESSION;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.commons.core.index.Index;
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

        String expectedMessage = String.format(DeleteSessionCommand.MESSAGE_DELETE_SESSION_SUCCESS, sessionToDelete);

        ModelManager expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
        expectedModel.deleteSession(sessionToDelete);

        assertDeleteSessionCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel);
    }

     @Test
     public void execute_invalidIndexUnfilteredList_throwsCommandException() {
         Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSessionList().size() + 1);
         DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(outOfBoundIndex);

         assertCommandFailure(deleteSessionCommand,
                 model, Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
     }

     @Test
     public void equals() {
         DeleteSessionCommand deleteFirstCommand = new DeleteSessionCommand(INDEX_FIRST_SESSION);
         DeleteSessionCommand deleteSecondCommand = new DeleteSessionCommand(INDEX_SECOND_SESSION);

         // same object -> returns true
         assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

         // same values -> returns true
         DeleteSessionCommand deleteFirstCommandCopy = new DeleteSessionCommand(INDEX_FIRST_SESSION);
         assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

         // different types -> returns false
         assertFalse(deleteFirstCommand.equals(1));

         // null -> returns false
         assertFalse(deleteFirstCommand.equals(null));

         // different sessions -> returns false
         assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
     }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoSession(Model model) {
        model.updateFilteredSessionList(p -> false);

        assertTrue(model.getFilteredSessionList().isEmpty());
    }

}

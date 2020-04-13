package cardibuddy.logic.commands;

import static cardibuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static cardibuddy.testutil.TypicalDecks.getTypicalCardiBuddy;
import static cardibuddy.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static cardibuddy.testutil.TypicalIndexes.INDEX_SECOND_DECK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.nio.file.Paths;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.Logic;
import cardibuddy.logic.LogicManager;
import cardibuddy.logic.LogicToUiManager;
import cardibuddy.model.Model;
import cardibuddy.model.ModelManager;
import cardibuddy.model.UserPrefs;
import cardibuddy.storage.CardiBuddyStorage;
import cardibuddy.storage.JsonCardiBuddyStorage;
import cardibuddy.storage.JsonUserPrefsStorage;
import cardibuddy.storage.Storage;
import cardibuddy.storage.StorageManager;
import cardibuddy.storage.UserPrefsStorage;
import cardibuddy.ui.Ui;
import cardibuddy.ui.UiManager;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteDeckCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(Paths.get("preferences.json"));
    UserPrefs userPrefs = new UserPrefs();
    CardiBuddyStorage cardiBuddyStorage = new JsonCardiBuddyStorage(userPrefs.getCardiBuddyFilePath());
    private Storage storage = new StorageManager(cardiBuddyStorage, userPrefsStorage);
    private Model model = new ModelManager(getTypicalCardiBuddy(), new UserPrefs());
    private Logic logic = new LogicManager(model, storage);
    private Ui ui = new UiManager(logic);
    private LogicToUiManager logicToUiManager = new LogicToUiManager((UiManager) ui);

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(outOfBoundIndex, logicToUiManager);

        assertCommandFailure(deleteDeckCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeckList().size() + 1);
        DeleteDeckCommand deleteDeckCommand = new DeleteDeckCommand(outOfBoundIndex, logicToUiManager);

        // execution failed -> cardibuddy state not added into model
        assertCommandFailure(deleteDeckCommand, model, commandHistory, Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        // single cardibuddy state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_UNDO);
        assertCommandFailure(new RedoCommand(), model, commandHistory, Messages.MESSAGE_NOTHING_TO_REDO);
    }

    @Test
    public void equals() {
        DeleteDeckCommand deleteDeckFirstCommand = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);
        DeleteDeckCommand deleteDeckSecondCommand = new DeleteDeckCommand(INDEX_SECOND_DECK, logicToUiManager);

        // same object -> returns true
        assertTrue(deleteDeckFirstCommand.equals(deleteDeckFirstCommand));

        // same values -> returns true
        DeleteDeckCommand deleteDeckFirstCommandCopy = new DeleteDeckCommand(INDEX_FIRST_DECK, logicToUiManager);
        assertTrue(deleteDeckFirstCommand.equals(deleteDeckFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteDeckFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteDeckFirstCommand.equals(null));

        // different deck -> returns false
        assertFalse(deleteDeckFirstCommand.equals(deleteDeckSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDeck(Model model) {
        model.updateFilteredDeckList(p -> false);

        assertTrue(model.getFilteredDeckList().isEmpty());
    }
}

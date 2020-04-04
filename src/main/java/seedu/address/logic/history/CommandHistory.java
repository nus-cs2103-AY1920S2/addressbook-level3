package seedu.address.logic.history;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.model.CommandHistoryState;
import seedu.address.storage.CommandHistoryStorage;

/**
 * This class represents the logic behind the command history and exposes the
 * methods needed for this feature.
 */
public class CommandHistory {

    private static final Path STORAGE_FILE_PATH = Paths.get("data", "command.txt");

    private CommandHistoryStorage historyStorage;
    private CommandHistoryState historyState;

    public CommandHistory() {
        historyStorage = new CommandHistoryStorage(STORAGE_FILE_PATH);
        historyState = new CommandHistoryState(historyStorage.readFromStorage());
    }

    /**
     * Adds the user input to the history, saving it to both the model
     * {@code CommandHistoryState} and the storage {@code CommandHistoryStorage}.
     *
     * @param command user input command to store
     */
    public void addToHistory(String command) {
        historyState.add(command);
        historyStorage.saveToStorage(historyState.getCurrentState());
    }

    /**
     * Returns the previous command while browsing the history.
     */
    public String getPreviousCommand() {
        return historyState.getPreviousCommand();
    }

    /**
     * Returns the next command while browsing the history.
     */
    public String getNextCommand() {
        return historyState.getNextCommand();
    }

    /**
     * Clears both the model {@code CommandHistoryState} and the storage
     * {@code CommandHistoryStorage}.
     */
    public void clearHistory() {
        historyState.clearState();
        historyStorage.clearStorage();
    }
}

package seedu.address.logic.history;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.model.CommandHistoryState;
import seedu.address.storage.CommandHistoryStorage;

/**
 * This class represents the logic behind the command history feature and acts
 * as an interface by exposing the necessary methods needed.
 */
public class CommandHistory {

    private static final Path DEFAULT_STORAGE_FILE_PATH = Paths.get("data", "command.txt");

    private CommandHistoryStorage historyStorage;
    private CommandHistoryState historyState;

    /**
     * Default constructor for this class which uses the default storage file path
     * for storage {@code CommandHistoryStorage}.
     */
    public CommandHistory() {
        historyStorage = new CommandHistoryStorage(DEFAULT_STORAGE_FILE_PATH);
        historyState = new CommandHistoryState(historyStorage.readFromStorage());
    }

    /**
     * Overloaded constructor for this class which allows the storage file path for
     * storage {@code CommandHistoryStorage} to be set. Provided for ease of JUnit
     * testing.
     */
    public CommandHistory(Path storagePath) {
        historyStorage = new CommandHistoryStorage(storagePath);
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
     * Returns the previous (least recent) command while browsing the history.
     */
    public String getPreviousCommand() {
        return historyState.getPreviousCommand();
    }

    /**
     * Returns the next (most recent) command while browsing the history.
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

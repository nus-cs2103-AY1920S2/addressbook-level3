package seedu.address.logic.history;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.CommandHistoryState;

/**
 * This class contains the logic behind the command history feature and exposes
 * the necessary methods needed for this feature to work.
 */
public class CommandHistory {
    private static final Logger logger = LogsCenter.getLogger(CommandHistory.class);

    private static final Path DEFAULT_STORAGE_FILE_PATH = Paths.get("data", "command.txt");
    private static final String EMPTY_FILE_CONTENT = "";

    private final Path storagePath;
    private CommandHistoryState historyState;

    /**
     * Default constructor for this class which uses the default storage file path
     * for the storage file.
     */
    public CommandHistory() {
        this(DEFAULT_STORAGE_FILE_PATH);
    }

    /**
     * Overloaded constructor for this class which allows the storage file path for
     * the storage file to be set. Provided for ease of JUnit testing.
     */
    public CommandHistory(Path storagePath) {
        requireNonNull(storagePath);
        this.storagePath = storagePath;

        try {
            FileUtil.createIfMissing(storagePath);
            String stateString = FileUtil.readFromFile(storagePath);
            historyState = new CommandHistoryState(storageStringToStateList(stateString));
            logger.info("Command History : successfully initialised command history");
        } catch (IOException ex) {
            // create the state anyways, in order to still use it even if storage is broken
            historyState = new CommandHistoryState(List.of(EMPTY_FILE_CONTENT));
            logger.warning("Command History : failed to read command history from storage");
            System.out.println(ex.getStackTrace());
        }
    }

    /**
     * Converts the storage text file into a list of strings where each new line in
     * the text file represents an element in the list.
     */
    public List<String> storageStringToStateList(String lines) {
        return Arrays.asList(lines.split("\n"));
    }

    /**
     * Converts the model list into a string where each item in the list is
     * delimitted by a new line in the joined string.
     */
    public String stateListToStorageString(List<String> list) {
        return String.join("\n", list);
    }

    /**
     * Adds the user input to the history, saving it to both the model via
     * {@code CommandHistoryState} and the storage via {@code FileUtil}.
     *
     * @param command user input command to store
     */
    public void addToHistory(String command) {
        historyState.add(command);
        try {
            FileUtil.writeToFile(storagePath, stateListToStorageString(historyState.getCurrentState()));
        } catch (IOException ex) {
            logger.warning("Command History : failed to write command history to storage");
            System.out.println(ex.getStackTrace());
        }
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
     * Clears both the model via {@code CommandHistoryState} and the storage file
     * via {@code FileUtil}.
     */
    public void clearHistory() {
        historyState.clearState();
        try {
            FileUtil.writeToFile(storagePath, EMPTY_FILE_CONTENT);
        } catch (IOException ex) {
            logger.warning("Command History : failed to clear command history from storage");
            System.out.println(ex.getStackTrace());
        }
    }
}

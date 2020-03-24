package csdev.couponstash.model.history;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores all the commandText that was executed in STASH. Does not matter
 * if the commands had errors, they are still stored and can be retrived by pressing up or down on
 * the keyboard.
 */
public class CommandTextHistory {
    private List<String> commandTextHistory;
    private int currIndex;

    public CommandTextHistory() {
        commandTextHistory = new ArrayList<>();
        commandTextHistory.add("");
        currIndex = 0;
    }

    /**
     * Add commandText to history.
     * @param commandText commandText to add to history
     */
    public void add(String commandText) {
        commandTextHistory.add(commandText);
        currIndex++;
    }

    /**
     * Undo the up button press.
     * @return commandText before up button was pressed
     */
    public String getDown() {
        if (currIndex == commandTextHistory.size() - 1) {
            return "";
        } else {
            return commandTextHistory.get(++currIndex);
        }
    }

    /**
     * Gets the last input commandText.
     * @return last input commandText.
     */
    public String getUp() {
        if (currIndex <= 1) {
            return commandTextHistory.get(currIndex);
        } else {
            return commandTextHistory.get(currIndex--);
        }
    }
}

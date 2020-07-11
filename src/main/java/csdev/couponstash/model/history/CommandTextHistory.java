package csdev.couponstash.model.history;

import java.util.LinkedList;

/**
 * This class stores all the commandText that was executed in STASH. Does not matter
 * if the commands had errors, they are still stored and can be retrieved by pressing up or down on
 * the keyboard.
 */
public class CommandTextHistory {
    private static final String EMPTY_COMMAND = "";

    private LinkedList<String> commandTextHistory;
    private int currIndex;

    public CommandTextHistory() {
        commandTextHistory = new LinkedList<>();
        commandTextHistory.push(EMPTY_COMMAND);
        currIndex = 0;
    }

    /**
     * Add commandText to history.
     * @param commandText commandText to add to history
     */
    public void add(String commandText) {
        commandTextHistory.pop(); // remove empty command from top of stack
        commandTextHistory.push(commandText); // add newest command to top of stack
        commandTextHistory.push(EMPTY_COMMAND); // add empty command to top of stack again
        currIndex = 0; // let index point to top of stack
    }

    /**
     * Undo the up button press.
     * @return commandText before up button was pressed
     */
    public String getDown() {
        return currIndex == 0
                ? EMPTY_COMMAND
                : commandTextHistory.get(--currIndex);
    }

    /**
     * Gets the last input commandText.
     * @return last input commandText.
     */
    public String getUp() {
        return currIndex == commandTextHistory.size() - 1
                ? commandTextHistory.get(currIndex) // No more previous command
                : commandTextHistory.get(++currIndex);
    }
}

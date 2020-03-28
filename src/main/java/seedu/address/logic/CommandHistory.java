package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private LinkedList<String> userInputHistory;

    public CommandHistory() {
        userInputHistory = new LinkedList<>();
    }

    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
    }

    /**
     * return a clone copy of userInputHistory list
     */
    public List<String> getHistory() {
        return new LinkedList<>(userInputHistory);
    }
}
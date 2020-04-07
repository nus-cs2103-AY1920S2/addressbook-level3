package cookbuddy.logic;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Helper class that is responsible for storing commands entered by user.
 */
public class CommandHistory {
    private final LinkedList<String> commandHistory = new LinkedList<String>();
    private ListIterator<String> iterator;
    private int maxSize;

    /**
     * Uses the default {@code maxSize} of 50 for history.
     */
    public CommandHistory() {
        maxSize = 50;
    }

    /**
     * Adds {@code cmd} to command history.
     * @param cmd The command to be stored in the history.
     */
    public void addCommand(String cmd) {
        commandHistory.addFirst(cmd);
        if (commandHistory.size() > maxSize) {
            commandHistory.removeLast();
        }
        this.iterator = null;
    }

    /**
     * Returns the command history size.
     * @return the size of the command history.
     */
    public int size() {
        return commandHistory.size();
    }

    /**
     * Resets the iterator.
     */
    public void resetIterator() {
        iterator = null;
    }

    /**
     * Returns the previous command.
     * @return the previous command.
     */
    public String iterateNext() {
        if (this.iterator == null) {
            this.iterator = commandHistory.listIterator(0);
        }
        if (this.iterator.hasNext()) {
            return this.iterator.next();
        }
        return null;
    }

    /**
     * Returns the next command.
     * @return the next command.
     */
    public String iteratePrevious() {
        if (this.iterator != null) {
            if (this.iterator.hasPrevious()) {
                this.iterator.previous();
            }
            if (this.iterator.hasPrevious()) {
                String result = this.iterator.previous();
                this.iterator.next();
                return result;
            } else {
                return "";
            }
        }
        return null;
    }
}

package seedu.address.model.util;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * To store user input strings.
 * Can use this pattern to implement undo/redo.
 */
public class UserInputHistory {
    private static LinkedList<String> list = new LinkedList<>();
    private static ListIterator<String> iterator = list.listIterator(0);
    private static String cached = "";

    /**
     * Saves user input to the history.
     */
    public static void saveInput(String input) {
        if (list.isEmpty() || !list.peek().equals(input)) {
            list.addFirst(input);
        }
        iterator = list.listIterator(0);
    }

    /**
     * User to scroll up in the history.
     */
    public static String goUp() {
        if (!iterator.hasNext()) {
            return cached;
        }

        cached = iterator.next();
        return cached;
    }

    /**
     * User to scroll down in the history.
     */
    public static String goDown() {
        if (!iterator.hasPrevious()) {
            return "";
        }

        cached = iterator.previous();
        return cached;
    }
}

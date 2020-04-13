package seedu.address.model.util;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Stores user input history as an ArrayList.
 */
public class CommandHistory {
    private static ArrayList<String> inputHistory = new ArrayList<>();
    private static ListIterator<String> iterator = inputHistory.listIterator();

    private static boolean previousCalled = false;
    private static boolean nextCalled = false;

    /**
     * Adds new user input and sets the iterator to point to the end of the list.
     */
    public static void addInput(String input) {
        inputHistory.add(input);
        iterator = inputHistory.listIterator(inputHistory.size());
        previousCalled = false;
        nextCalled = false;
    }

    /**
     * Retrieves the last input command in the list.
     * If next has been called recently, previous need to be called twice due to Java's implementation of
     * ListIterator where altering calls between next and previous returning the same element.
     */
    public static String getPrevious() {
        if (nextCalled && iterator.hasPrevious()) {
            iterator.previous();
            nextCalled = false;
        }
        if (iterator.hasPrevious()) {
            previousCalled = true;
            return iterator.previous();
        } else {
            return "";
        }
    }

    /**
     * Retrieves the next input command in the list.
     */
    public static String getNext() {
        if (previousCalled && iterator.hasNext()) {
            iterator.next();
            previousCalled = false;
        }
        if (iterator.hasNext()) {
            nextCalled = true;
            return iterator.next();
        } else {
            return "";
        }
    }
}

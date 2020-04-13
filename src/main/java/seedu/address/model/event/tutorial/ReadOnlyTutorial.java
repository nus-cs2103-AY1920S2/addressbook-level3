package seedu.address.model.event.tutorial;

import java.util.List;

/**
 * Unmodifiable view of all Tutorials.
 */

public interface ReadOnlyTutorial {
    /**
     * Returns an unmodifiable view of the tutorials list.
     * This list will not contain any duplicate tutorials.
     */
    List<Tutorial> getAllTutorials();
}

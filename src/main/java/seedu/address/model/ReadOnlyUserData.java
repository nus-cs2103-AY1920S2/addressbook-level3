package seedu.address.model;

import seedu.address.model.person.User;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyUserData {

    /**
     * Returns the user.
     */
    User getUser();

    /**
     * Returns true if the user data is empty.
     */
    boolean isEmpty();

}

package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.User;

/**
 * Wraps all data of the user
 */
public class UserData implements ReadOnlyUserData {

    private final User user;

    public UserData(User user) {
        this.user = user;
    }

    public UserData() {
        this.user = new User();
    }

    /**
     * Creates an UserData using the data in the {@code toBeCopied}
     */
    public UserData(ReadOnlyUserData toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code UserData} with {@code newUserData}.
     */
    public void resetData(ReadOnlyUserData newUserData) {
        requireNonNull(newUserData);

        this.user.setUser(newUserData.getUser());
    }

    @Override
    public boolean isEmpty() {
        return user.getName() == null && user.getPhone() == null
                && user.getEmail() == null;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserData // instanceof handles nulls
                && user.equals(((UserData) other).user));
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }
}

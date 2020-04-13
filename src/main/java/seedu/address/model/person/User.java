package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents the user.
 */
public class User {

    private Name name;
    private Phone phone;
    private Email email;

    /**
     * Every field must be present and not null.
     */
    public User(Name name, Phone phone, Email email) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Represents an empty user data.
     */
    public User() {

    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Replaces the user with {@code newUser}.
     */
    public void setUser(User newUser) {
        this.name = newUser.getName();
        this.phone = newUser.getPhone();
        this.email = newUser.getEmail();
    }

    /**
     * Returns true if both users have the same data.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return otherUser.getName().equals(getName())
                && otherUser.getPhone().equals(getPhone())
                && otherUser.getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User: ")
                .append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail());
        return builder.toString();
    }

}

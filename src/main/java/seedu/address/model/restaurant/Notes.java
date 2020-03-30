package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Restaurant's name in the restaurant book.
 * Guarantees: immutable.
 */
public class Notes {

    public final String note;

    /**
     * Constructs a {@code Notes}.
     *
     * @param note A valid note.
     */
    public Notes(String note) {
        requireNonNull(note);
        this.note = note;
    }

    @Override
    public String toString() {
        return note;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Notes // instanceof handles nulls
                && note.equals(((Notes) other).note)); // state check
    }

    @Override
    public int hashCode() {
        return note.hashCode();
    }

}

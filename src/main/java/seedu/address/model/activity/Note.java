package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an activity's notes.
 * Guarantees: immutable, is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS =
            "Notes should not be empty and must contain"
                    + "at least one non-whitespace character.";

    /*
     * The notes String must contain at least one non-whitespace
     * character, otherwise a bunch of tabs and spaces will
     * qualify as a note which should not be the case.
     */
    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String notes;

    /**
     * Constructs a {@code Note}.
     *
     * @param notes Valid notes.
     */
    public Note(String notes) {
        requireNonNull(notes);
        checkArgument(isValidNote(notes), MESSAGE_CONSTRAINTS);
        this.notes = notes;
    }

    /**
     * Returns true if a given string is considered valid notes.
     */
    public static boolean isValidNote(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return notes;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && notes.equals(((Note) other).notes)); // state check
    }

    @Override
    public int hashCode() {
        return notes.hashCode();
    }
}

package seedu.delino.model.parcel.comment;

import static java.util.Objects.requireNonNull;
import static seedu.delino.commons.util.AppUtil.checkArgument;

//@@author Exeexe93
/**
 * Represents a comment for the order or return order in order book or return order book respectively.
 * Guarantees: immutable; name is valid as declared in {@link #isValidComment(String)}
 */

public class Comment {
    public static final String MESSAGE_CONSTRAINTS = "Comment can take any values, and it should not be blank";

    /*
     * The first character of the Comment must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String commentMade;

    /**
     * Constructs a {@code Comment}.
     *
     * @param commentMade A valid comment.
     */
    public Comment(String commentMade) {
        requireNonNull(commentMade);
        checkArgument(isValidComment(commentMade), MESSAGE_CONSTRAINTS);
        this.commentMade = commentMade;
    }

    /**
     * Returns true if a given string is a valid comment.
     */
    public static boolean isValidComment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Comment
                && commentMade.equals(((Comment) other).commentMade));
    }

    @Override
    public int hashCode() {
        return commentMade.hashCode();
    }

    public String toString() {
        return commentMade;
    }
}

package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's name in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid quote ! Quote should only contain alphanumeric characters, spaces, or special characters from "
                    + "this set {&, %, (, ), -, /, ', , !,}. The name should also not be blank.";

    //TODO need to activate validation to accept exclamation mark
    public static final String VALIDATION_REGEX = "^[\\sA-Za-z0-9\\()!&%/',-]+$+";

    public final String fullContent;

    /**
     * Constructs a {@code Content}.
     *
     * @param content A valid quote content.
     */
    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidContent(content), MESSAGE_CONSTRAINTS);
        this.fullContent = content;
    }

    /**
     * Returns true if a given string is valid content.
     */
    public static boolean isValidContent(String test) {
        return (!test.isBlank()) && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullContent;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.recipe.model.achievement.Content // instanceof handles nulls
                && fullContent.equals(((seedu.recipe.model.achievement.Content) other).fullContent)); // state check
    }

    @Override
    public int hashCode() {
        return fullContent.hashCode();
    }

}

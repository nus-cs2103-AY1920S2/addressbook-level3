package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;

/**
 * Quote command to display quote
 */
public class Quote {

    private final String content;

    public Quote(String quote) {
        requireNonNull(quote);
        //checkArgument(isValidGoalName(quote), MESSAGE_CONSTRAINTS);
        this.content = quote;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quote // instanceof handles nulls
                && content.equals(((Quote) other).content)); // state check
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    public String getContent() {
        return content;
    }
}

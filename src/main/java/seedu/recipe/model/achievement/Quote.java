package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;

/**
 * Quote command to display quote
 */
public class Quote {

    private final Content content;

    public Quote(Content quote) {
        requireNonNull(quote);
        //checkArgument(isValidGoalName(quote), MESSAGE_CONSTRAINTS);
        this.content = quote;
    }

    @Override
    public String toString() {
        return content.toString();
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
        return content.toString();
    }

    /**
     * Check if quote is the same as the other quote
     * @param otherQuote
     * @return
     */
    public boolean isSameQuote(Quote otherQuote) {
        if (otherQuote == this) {
            return true;
        }

        return otherQuote != null
                && otherQuote.getContent().equals(getContent());
    }

}

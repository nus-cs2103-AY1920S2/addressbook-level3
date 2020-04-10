package seedu.recipe.model;

import javafx.collections.ObservableList;
import seedu.recipe.model.achievement.Quote;

/**
 * Unmodifiable view of a quotes book
 */
public interface ReadOnlyQuoteBook {
    /**
     * Returns an unmodifiable view of the quote list.
     * This list will not contain any duplicate quotes.
     */
    ObservableList<Quote> getQuoteList();

}

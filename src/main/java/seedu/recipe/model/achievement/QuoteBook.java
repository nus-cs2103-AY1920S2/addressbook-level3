package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.recipe.model.ReadOnlyQuoteBook;
import seedu.recipe.model.recipe.RecipeBook;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameQuote comparison)
 */
public class QuoteBook implements ReadOnlyQuoteBook {
    private final UniqueQuoteList quotes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        quotes = new UniqueQuoteList();
    }

    public QuoteBook() {}

    /**
     * Creates a QuoteBook using the Recipes in the {@code toBeCopied}
     */
    public QuoteBook(ReadOnlyQuoteBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the quote list with {@code quotes}.
     * {@code quotes} must not contain duplicate quotes.
     */
    public void setQuotes(List<Quote> quotes) {
        this.quotes.setQuotes(quotes);
    }

    /**
     * Resets the existing data of this {@code QuoteBook} with {@code newData}.
     */
    public void resetData(ReadOnlyQuoteBook newData) {
        requireNonNull(newData);

        setQuotes(newData.getQuoteList());
    }

    /**
     * Returns true if a quote with the same identity as {@code quote} exists in the quotebook.
     */
    public boolean hasQuote(Quote quote) {
        requireNonNull(quote);
        return quotes.contains(quote);
    }

    /**
     * Adds a record to the quotebook.
     * The record must not already exist in the address book.
     */
    public void addQuote(Quote q) {
        quotes.add(q);
    }

    /**
     * Replaces the given quote {@code target} in the list with {@code editedQuote}.
     * {@code target} must exist in the address book.
     * The quote identity of {@code editedQuote} must not be the same as another existing quote in the address book.
     */
    public void setQuote(Quote target, Quote editedQuote) {
        requireNonNull(editedQuote);

        quotes.setQuote(target, editedQuote);
    }

    //// util methods

    @Override
    public String toString() {
        return quotes.asUnmodifiableObservableList().size() + " quotes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Quote> getQuoteList() {
        return quotes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeBook // instanceof handles nulls
                && quotes.equals(((QuoteBook) other).quotes));
    }

    @Override
    public int hashCode() {
        return quotes.hashCode();
    }
}

package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of quotes that enforces uniqueness between its elements and does not allow nulls.
 * A quote is considered unique by comparing using {@code Quote#isSameQuote(Quote)}. As such, adding and updating of
 * quotes uses Quote#isSameQuote(Quote) for equality so as to ensure that the quote being added or updated is
 * unique in terms of identity in the UniqueQuoteList. However, the removal of a quote uses Quote#equals(Quote) so
 * as to ensure that the quote with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Quote#isSameQuote(Quote)
 */
public class UniqueQuoteList implements Iterable<Quote> {
    private final ObservableList<Quote> internalList = FXCollections.observableArrayList();
    private final ObservableList<Quote> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Replaces the contents of this list with {@code quote}.
     * {@code quotes} must not contain duplicate quotes.
     */
    public void setQuotes(List<Quote> quotes) {
        requireAllNonNull(quotes);
        if (!quotesAreUnique(quotes)) {
            throw new DuplicateQuoteException();
        }

        internalList.clear();
        internalList.setAll(quotes);
    }

    /**
     * Replaces the quote {@code target} in the list with {@code editedQuote}.
     * {@code target} must exist in the list.
     * The quote identity of {@code editedQuote} must not be the same as another existing quote in the list.
     */
    public void setQuote(Quote target, Quote editedQuote) {
        requireAllNonNull(target, editedQuote);

        int index = internalList.indexOf(target);
        if (index == -1) {
            //exception to be changed
            throw new QuoteNotFoundException();
        }

        if (!target.isSameQuote(editedQuote) && contains(editedQuote)) {
            throw new DuplicateQuoteException();
        }

        internalList.set(index, editedQuote);
    }

    /**
     * Returns true if the list contains an equivalent recipe as the given argument.
     */
    public boolean contains(Quote toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameQuote);
    }

    /**
     * Adds a recipe to the list.
     * The recipe must not already exist in the list.
     */
    public void add(Quote toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateQuoteException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Quote> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Quote> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if {@code quotes} contains only unique quotes.
     */
    private boolean quotesAreUnique(List<Quote> quotes) {
        for (int i = 0; i < quotes.size() - 1; i++) {
            for (int j = i + 1; j < quotes.size(); j++) {
                if (quotes.get(i).isSameQuote(quotes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueQuoteList // instanceof handles nulls
                && internalList.equals(((UniqueQuoteList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

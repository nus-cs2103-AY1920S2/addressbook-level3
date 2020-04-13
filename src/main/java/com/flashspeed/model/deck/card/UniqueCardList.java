package com.flashspeed.model.deck.card;

import static com.flashspeed.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.model.deck.exceptions.CardNotFoundException;
import com.flashspeed.model.deck.exceptions.DuplicateCardException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of cards that enforces uniqueness between its elements and does not allow nulls.
 * A card is considered unique by comparing using {@code Card#equals(Object)}. As such, adding and updating of
 * cards uses Card#equals(Object) for equality so as to ensure that the card being added or updated is
 * unique in terms of content in the UniqueCardList. The removal of a card also uses Card#equals(Object)
 *
 * Supports a minimal set of list operations.
 *
 * @see Card#equals(Object)
 */
public class UniqueCardList implements Iterable<Card> {

    private final ObservableList<Card> internalList = FXCollections.observableArrayList();
    private final ObservableList<Card> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent card as the given argument.
     */
    public boolean contains(Card toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    public int getSize() {
        return internalList.size();
    }

    /**
     * Adds a card to the list.
     * The card must not already exist in the list.
     */
    public void add(Card toAdd) throws DuplicateCardException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCardException();
        }
        internalList.add(toAdd);
    }

    public Card getCard(Index index) {
        return internalList.get(index.getZeroBased());
    }

    /**
     * Removes the equivalent card from the list.
     * The card must exist in the list.
     */
    public void remove(Card toRemove) throws DuplicateCardException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CardNotFoundException();
        }
    }

    /**
     * Replaces the card {@code toRemove} in the list with {@code toAdd}. {@code toRemove} must exist in the list.
     * The card identity of {@code toAdd} must not be the same as another existing card in the list.
     */
    public void replace(Card toRemove, Card toAdd) throws CardNotFoundException, DuplicateCardException {
        requireAllNonNull(toRemove, toAdd);

        int idx = internalList.indexOf(toRemove);
        if (idx == -1) {
            throw new CardNotFoundException();
        }
        if (!toRemove.equals(toAdd) && contains(toAdd)) {
            throw new DuplicateCardException();
        }

        internalList.set(idx, toAdd);
    }

    public void setCards(UniqueCardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Card> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public ObservableList<Card> asObservableList() {
        return FXCollections.observableArrayList(internalList);
    }

    @Override
    public Iterator<Card> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCardList // instanceof handles nulls
                && internalList.equals(((UniqueCardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code cards} contains only unique cards.
     */
    private boolean cardsAreUnique(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).equals(cards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

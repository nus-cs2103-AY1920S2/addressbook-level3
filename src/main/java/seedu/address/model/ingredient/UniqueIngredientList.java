package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * A list of ingredients that enforces uniqueness between its elements and does not allow nulls.
 * An ingredient is considered unique by comparing using {@code Ingredient#isSameIngredient(Ingredient)}. As such,
 * adding and updating of ingredients uses {@code Ingredient#isSameIngredient(Ingredient)} for equality so as to ensure
 * that the ingredient being added or updated is unique in terms of identity in the UniqueIngredientList.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Ingredient#isSameIngredient(Ingredient)
 */
public class UniqueIngredientList implements Iterable<Ingredient> {

    protected final ObservableList<Ingredient> internalList = FXCollections.observableArrayList();
    protected final ObservableList<Ingredient> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns the size of the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains an ingredient equivalent to the given argument.
     */
    public boolean contains(Ingredient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameIngredient);
    }

    /**
     * Returns an ingredient equivalent to given argument, if the list contains an equivalent ingredient.
     */
    public Ingredient find(Ingredient toFind) {
        checkArgument(contains(toFind));
        return internalList.stream().filter(toFind::isSameIngredient).findFirst().get();
    }

    /**
     * Adds an ingredient to the list.
     */
    public void add(Ingredient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            int index = internalList.indexOf(find(toAdd));
            internalList.set(index, find(toAdd).add(toAdd));
        } else {
            internalList.add(toAdd);
        }
    }

    /**
     * Replaces the ingredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the list.
     * The ingredient identity of {@code editedIngredient} must not be the same as another existing ingredient in the
     * list.
     */
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new IngredientNotFoundException();
        }

        if (!target.isSameIngredient(editedIngredient) && contains(editedIngredient)) {
            remove(target);
            add(editedIngredient);
        } else {
            internalList.set(index, editedIngredient);
        }
    }

    /**
     * Removes the quantity of the ingredient from the list.
     * The ingredient must exist in the list. If the resulting quantity is not positive, the ingredient will be removed
     * from the list entirely.
     */
    public void remove(Ingredient toRemove) {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new IngredientNotFoundException();
        }

        Ingredient originalIngredient = find(toRemove);
        if (toRemove.equals(originalIngredient)) {
            internalList.remove(originalIngredient);
        } else {
            Ingredient subtractedIngredient = originalIngredient.subtract(toRemove);
            setIngredient(originalIngredient, subtractedIngredient);
        }
    }

    /**
     * Removes all of the ingredient with the ingredient name from the list.
     * The ingredient must exist in the list.
     */
    public void remove(IngredientName toRemove) {
        requireNonNull(toRemove);
        boolean hasIngredient = internalList.stream().anyMatch(x -> toRemove.equals(x.getName()));
        if (!hasIngredient) {
            throw new IngredientNotFoundException();
        }

        this.setIngredients(internalList.stream()
                .filter(x -> !toRemove.equals(x.getName()))
                .collect(Collectors.toList()));
    }

    public void setIngredients(UniqueIngredientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code ingredients}.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        requireAllNonNull(ingredients);
        internalList.clear();
        for (Ingredient ingredient : ingredients) {
            add(ingredient);
        }
    }

    /**
     * Sorts the list of ingredients using the specified comparator.
     */
    public void sort(Comparator<? super Ingredient> comparator) {
        Collections.sort(internalList, comparator);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Ingredient> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Ingredient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueIngredientList // instanceof handles nulls
                && internalList.equals(((UniqueIngredientList) other).internalList));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (internalList.size() > 0) {
            int i;
            for (i = 0; i < internalList.size() - 1; i++) {
                sb.append(internalList.get(i).toString()).append(", ");
            }

            sb.append(internalList.get(i).toString());
        }

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

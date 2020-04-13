package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recipe.exceptions.DuplicateRecipeException;
import seedu.address.model.recipe.exceptions.RecipeNotFoundException;


/**
 * A list of recipes that enforces uniqueness between its elements and does not allow nulls.
 * A recipe is considered unique by comparing using {@code Recipe#isSameRecipe(Recipe)}. As such, adding and updating of
 * recipes uses Recipe#isSameRecipe(Recipe) for equality so as to ensure that the recipe being added or updated is
 * unique in terms of identity in the UniqueRecipeList. However, the removal of a recipe uses Recipe#equals(Object) so
 * as to ensure that the recipe with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Recipe#isSameRecipe(Recipe)
 */
public class UniqueRecipeList implements Iterable<Recipe> {

    private final ObservableList<Recipe> internalList = FXCollections.observableArrayList();
    private final ObservableList<Recipe> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Checks if otherRecipe already exists in the list.*
     * @return true is otherRecipe exists, false otherwise.
     */
    public boolean contains(Recipe otherRecipe) {
        requireNonNull(otherRecipe);
        return internalList.stream().anyMatch(otherRecipe::isSameRecipe);
    }

    /**
     * Adds an recipe to the list if it does not yet exist.
     */
    public void add(Recipe toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecipeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the list.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the list.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecipeNotFoundException();
        }

        if (!target.isSameRecipe(editedRecipe) && contains(editedRecipe)) {
            throw new DuplicateRecipeException();
        }

        internalList.set(index, editedRecipe);
    }

    /**
     * Replaces all the current recipes in UniqueRecipeList and replaces it with {@code replacement}.
     * {@code replacement} must exist.
     *
     * @param replacement
     */
    public void setRecipes(UniqueRecipeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setRecipes(List<Recipe> recipes) {
        requireAllNonNull(recipes);
        if (!recipesAreUnique(recipes)) {
            throw new DuplicateRecipeException();
        }

        internalList.setAll(recipes);
    }

    /**
     * Removes the recipe from the list, provided it exists.
     */
    public void remove(Recipe toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecipeNotFoundException();
        }
    }

    /**
     * Sorts the list of recipes using the specified comparator.
     */
    public void sort(Comparator<? super Recipe> comparator) {
        Collections.sort(internalList, comparator);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Recipe> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Recipe> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if {@code recipes} contains only unique recipes.
     */
    private boolean recipesAreUnique(List<Recipe> recipes) {
        for (int i = 0; i < recipes.size() - 1; i++) {
            for (int j = i + 1; j < recipes.size(); j++) {
                if (recipes.get(i).isSameRecipe(recipes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRecipeList // instanceof handles nulls
                && internalList.equals(((UniqueRecipeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

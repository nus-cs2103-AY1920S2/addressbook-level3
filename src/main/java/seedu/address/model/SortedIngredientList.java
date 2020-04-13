package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.ingredient.CompatibleIngredientList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientDefaultComparator;
import seedu.address.model.ingredient.IngredientName;

/**
 * Wraps all data at the ingredient list level
 */
public abstract class SortedIngredientList {

    private final CompatibleIngredientList ingredients = new CompatibleIngredientList();

    /**
     * Replaces the contents of the ingredient list with {@code ingredients}.
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setIngredients(ingredients);
        sort();
    }

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the list of ingredients.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return ingredients.contains(ingredient);
    }

    /**
     * Adds an ingredient to the list of ingredients.
     * The ingredient must not already exist in the list of ingredients.
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        sort();
    }

    /**
     * Replaces the given ingredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the list of ingredients.
     * The ingredient identity of {@code editedIngredient} must not be the same as another existing ingredient in the
     * list of ingredients.
     */
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireNonNull(editedIngredient);
        ingredients.setIngredient(target, editedIngredient);
        sort();
    }

    /**
     * Removes {@code key} from this {@code ingredientList}.
     * {@code key} must exist in the list of ingredients.
     */
    public void removeIngredient(Ingredient key) {
        ingredients.remove(key);
    }

    /**
     * Removes ingredients with the name {@code key} from this {@code ingredientList}.
     * {@code key} must exist in the list of ingredients.
     */
    public void removeIngredient(IngredientName key) {
        ingredients.remove(key);
    }

    /**
     * Sorts the list of ingredients using {@code IngredientDefaultComparator}.
     */
    public void sort() {
        sort(new IngredientDefaultComparator());
    }

    /**
     * Sorts the list of ingredients using the specified comparator.
     */
    public void sort(Comparator<? super Ingredient> comparator) {
        ingredients.sort(comparator);
    }

    public CompatibleIngredientList getCompatibleIngredientList() {
        return ingredients;
    }

    public ObservableList<Ingredient> getIngredientList() {
        return ingredients.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        return ingredients.asUnmodifiableObservableList().size() + " ingredients";
    }
}

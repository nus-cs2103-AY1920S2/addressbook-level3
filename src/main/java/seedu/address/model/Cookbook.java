package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDefaultComparator;
import seedu.address.model.recipe.UniqueRecipeList;

/**
 * Wraps all data at the cookbook level.
 * Duplicates are not allowed (by .isSameRecipe comparison)
 */
public class Cookbook implements ReadOnlyCookbook {

    private final UniqueRecipeList recipes;

    public Cookbook() {
        recipes = new UniqueRecipeList();
    }

    /**
     * Creates an Cookbook using the Recipes in the {@code toBeCopied}
     */
    public Cookbook(ReadOnlyCookbook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the recipe list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes.setRecipes(recipes);
    }

    /**
     * Resets the existing data of this {@code Cookbook} with {@code newData}.
     */
    public void resetData(ReadOnlyCookbook newData) {
        requireNonNull(newData);

        setRecipes(newData.getRecipeList());
    }

    //// recipe-level operations

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the cookbook.
     */
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipes.contains(recipe);
    }

    /**
     * Adds a recipe to the cookbook.
     * The recipe must not already exist in the cookbook.
     */
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        recipes.sort(new RecipeDefaultComparator());
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the cookbook.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the cookbook.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireNonNull(editedRecipe);
        recipes.setRecipe(target, editedRecipe);
        recipes.sort(new RecipeDefaultComparator());
    }

    /**
     * Removes {@code key} from this {@code Cookbook}.
     * {@code key} must exist in the cookbook.
     */
    public void removeRecipe(Recipe key) {
        recipes.remove(key);
    }

    /**
     * Sorts the cookbook by similarity of the specified inventory's ingredients.
     */
    public void sort(Comparator<? super Recipe> comparator) {
        recipes.sort(comparator);
    }

    //// util methods

    @Override
    public String toString() {
        return recipes.asUnmodifiableObservableList().size() + " recipes";
    }

    @Override
    public ObservableList<Recipe> getRecipeList() {
        return recipes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cookbook // instanceof handles nulls
                && recipes.equals(((Cookbook) other).recipes));
    }

    @Override
    public int hashCode() {
        return recipes.hashCode();
    }
}

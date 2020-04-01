package cookbuddy.model;

import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.UniqueRecipeList;
import javafx.collections.ObservableList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the recipe-book level
 * Duplicates are not allowed (by .isSameRecipe comparison)
 */
public class RecipeBook implements ReadOnlyRecipeBook {

    private final UniqueRecipeList recipes;

    /**
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        recipes = new UniqueRecipeList();
    }

    public RecipeBook() {
    }

    /**
     * Creates an RecipeBook using the Recipes in the {@code toBeCopied}
     */
    public RecipeBook(ReadOnlyRecipeBook toBeCopied) {
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
     * Resets the existing data of this {@code RecipeBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRecipeBook newData) {
        requireNonNull(newData);

        setRecipes(newData.getRecipeList());
    }

    //// recipe-level operations

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the recipe book.
     */
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipes.contains(recipe);
    }

    /**
     * Returns the total number of recipes in the recipe book.
     */
    public long count() {
        return recipes.count();
    }

    /**
     * favourites a recipe from the recipe book.
     * @param recipe the recipe to be favourited.
     */
    public void fav(Recipe recipe) {
        requireNonNull(recipe);
        recipe.favRecipe();
    }

    /**
     * Un-favourites a recipe from the recipe book.
     * @param recipe the recipe to be un-favourited.
     */
    public void unFav(Recipe recipe) {
        requireNonNull(recipe);
        recipe.unFavRecipe();
    }

    public void attempt(Recipe recipe) {
        requireNonNull(recipe);
        recipe.attemptRecipe();
    }


    /**
     * Adds a recipe to the recipe book.
     * The recipe must not already exist in the recipe book.
     */
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in the recipe book.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the recipe book.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireNonNull(editedRecipe);
        if(target.getFavStatus().toString().equals("\u2665")) {
            editedRecipe.favRecipe();
        }

        if(target.getDoneStatus().toString().equals("Yes")) {
            editedRecipe.attemptRecipe();
        }

        recipes.setRecipe(target, editedRecipe);
    }

    /**
     * Removes {@code key} from this {@code RecipeBook}.
     * {@code key} must exist in the recipe book.
     */
    public void removeRecipe(Recipe key) {
        recipes.remove(key);
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
               || (other instanceof RecipeBook // instanceof handles nulls
                   && recipes.equals(((RecipeBook) other).recipes));
    }

    @Override
    public int hashCode() {
        return recipes.hashCode();
    }
}

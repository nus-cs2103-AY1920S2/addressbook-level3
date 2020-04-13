package seedu.recipe.testutil;

import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.RecipeBook;

/**
 * A utility class to help with building RecipeBook objects.
 * Example usage: <br>
 *     {@code RecipeBook ab = new RecipeBookBuilder().withRecipe("John", "Doe").build();}
 */
public class RecipeBookBuilder {

    private RecipeBook recipeBook;

    public RecipeBookBuilder() {
        recipeBook = new RecipeBook();
    }

    public RecipeBookBuilder(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    /**
     * Adds a new {@code Recipe} to the {@code RecipeBook} that we are building.
     */
    public RecipeBookBuilder withRecipe(Recipe recipe) {
        recipeBook.addRecipe(recipe);
        return this;
    }

    public RecipeBook build() {
        return recipeBook;
    }
}

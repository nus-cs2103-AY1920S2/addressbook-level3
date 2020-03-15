package seedu.address.testutil;

import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class to help with building Recipebook objects.
 * Example usage: <br>
 * {@code RecipeBook ab = new RecipeBookBuilder().withPerson("John", "Doe").build();}
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
    public RecipeBookBuilder withPerson(Recipe recipe) {
        recipeBook.addRecipe(recipe);
        return this;
    }

    public RecipeBook build() {
        return recipeBook;
    }
}

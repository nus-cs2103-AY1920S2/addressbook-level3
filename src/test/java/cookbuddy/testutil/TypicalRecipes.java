package cookbuddy.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cookbuddy.logic.commands.CommandTestUtil;
import cookbuddy.model.RecipeBook;
import cookbuddy.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe HAM_SANDWICH = new RecipeBuilder().withName(CommandTestUtil.VALID_NAME_HAM_SANDWICH).withIngredients(
            CommandTestUtil.VALID_INGREDIENTS_HAM_SANDWICH).withInstructions(
            CommandTestUtil.VALID_INSTRUCTIONS_HAM_SANDWICH).withTags(CommandTestUtil.VALID_TAG_LUNCH)
            .build();

    public static final Recipe EGGS_ON_TOAST = new RecipeBuilder().withName(CommandTestUtil.VALID_NAME_EGGS_ON_TOAST).withIngredients(
            CommandTestUtil.VALID_INGREDIENTS_EGGS_ON_TOAST).withInstructions(
            CommandTestUtil.VALID_INSTRUCTIONS_EGGS_ON_TOAST).withTags(
            CommandTestUtil.VALID_TAG_BREAKFAST, CommandTestUtil.VALID_TAG_LUNCH).build();

    // TODO: Add more typical recipes

    public static final String KEYWORD_MATCHING_HAM = "Ham"; // A keyword that matches MEIER

    private TypicalRecipes() {
    } // prevents instantiation

    /**
     * Returns an {@code RecipeBook} with all the typical persons.
     */
    public static RecipeBook getTypicalRecipeBook() {
        RecipeBook rb = new RecipeBook();
        for (Recipe recipe : getTypicalRecipes()) {
            rb.addRecipe(recipe);
        }
        return rb;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(HAM_SANDWICH, EGGS_ON_TOAST));
    }
}

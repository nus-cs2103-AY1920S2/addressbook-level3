package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENTS_EGGS_ON_TOAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENTS_HAM_SANDWICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTIONS_EGGS_ON_TOAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTIONS_HAM_SANDWICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EGGS_ON_TOAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HAM_SANDWICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LUNCH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe HAM_SANDWICH = new RecipeBuilder().withName(VALID_NAME_HAM_SANDWICH).withIngredients(
            VALID_INGREDIENTS_HAM_SANDWICH).withInstructions(VALID_INSTRUCTIONS_HAM_SANDWICH).withTags(VALID_TAG_LUNCH)
            .build();

    public static final Recipe EGGS_ON_TOAST = new RecipeBuilder().withName(VALID_NAME_EGGS_ON_TOAST).withIngredients(
            VALID_INGREDIENTS_EGGS_ON_TOAST).withInstructions(VALID_INSTRUCTIONS_EGGS_ON_TOAST).withTags(
            VALID_TAG_BREAKFAST, VALID_TAG_LUNCH).build();

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

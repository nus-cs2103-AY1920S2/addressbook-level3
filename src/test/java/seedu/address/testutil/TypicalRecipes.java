package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Cookbook;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.Step;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    private static final Recipe[] TYPICAL_RECIPES = SampleDataUtil.getSampleRecipes();
    public static final Recipe SCRAMBLED_EGG = TYPICAL_RECIPES[0];
    public static final Recipe CARBONARA = TYPICAL_RECIPES[1];
    public static final Recipe AGLIO_OLIO = TYPICAL_RECIPES[2];
    public static final Recipe SPAGHETTI_BOLOGNESE = TYPICAL_RECIPES[3];


    private TypicalRecipes() {} // prevents instantiation

    /**
     * Returns a {@code Recipe} with sample ingredients, steps, and tags.
     */
    public static Recipe getValidRecipe() {
        UniqueIngredientList ingredients = new UniqueIngredientList();
        ingredients.add(new Ingredient(new IngredientName("Ingredient"), new IngredientQuantity("5")));
        UniqueStepList steps = new UniqueStepList();
        steps.add(new Step("Step"));
        return new Recipe(
                new RecipeName("Recipe"),
                new RecipeDescription("Description"),
                ingredients,
                steps,
                SampleDataUtil.getTagSet("Tag"));
    }

    /**
     * Returns a {@code Cookbook} with all the typical recipes.
     */
    public static Cookbook getTypicalCookbook() {
        Cookbook cookbook = new Cookbook();

        for (Recipe recipe : getTypicalRecipes()) {
            cookbook.addRecipe(recipe);
        }

        return cookbook;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(AGLIO_OLIO, CARBONARA, SCRAMBLED_EGG, SPAGHETTI_BOLOGNESE));
    }
}

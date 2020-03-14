package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_STEP_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STEP_TURKEY_SANDWICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_GRAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_PROTEIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TURKEY_SANDWICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_TURKEY_SANDWICH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe CAESAR_SALAD = new RecipeBuilder().withName("Caesar Salad")
            .withStep("alice@example.com")
            .withTime("30")
            .withIngredients("Tomato, 100, vegetable", "Honeydew, 100, other")
            .withGoals("Herbivore").build();

    public static final Recipe GRILLED_SANDWICH = new RecipeBuilder().withName("Grilled Sandwich")
            .withStep("johnd@example.com")
            .withTime("10")
            .withIngredients("Bread, 50, grain", "Cheese, 50, other")
            .withGoals("Wholesome Wholemeal").build();

    public static final Recipe BOILED_CHICKEN = new RecipeBuilder().withName("Boiled Chicken")
            .withTime("10")
            .withIngredients("Chicken, 100, protein")
            .withGoals("Bulk like the Hulk")
            .withStep("heinz@example.com").build();

    public static final Recipe CHOCOLATE_CAKE = new RecipeBuilder().withName("Chocolate Cake")
            .withTime("120")
            .withStep("cornelia@example.com").build();

    public static final Recipe OMELET = new RecipeBuilder().withName("Omelet")
            .withTime("15")
            .withStep("werner@example.com").build();

    public static final Recipe STEAMED_EGG = new RecipeBuilder().withName("Steamed Egg")
            .withTime("15")
            .withStep("lydia@example.com").build();

    public static final Recipe TUNA_BREAD = new RecipeBuilder().withName("Tuna Bread")
            .withTime("60")
            .withGoals("Bulk like the Hulk", "Wholesome Wholemeal")
            .withStep("anna@example.com").build();

    // Manually added
    public static final Recipe FISH_TACO = new RecipeBuilder().withName("Fish Taco").withTime("60")
            .withStep("stefan@example.com").build();
    public static final Recipe VEGETARIAN_PIZZA = new RecipeBuilder().withName("Vegetarian Pizza").withTime("45")
            .withGoals("Herbivore").withStep("hans@example.com").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil} todo: add ingredients
    public static final Recipe TURKEY_SANDWICH = new RecipeBuilder().withName(VALID_NAME_TURKEY_SANDWICH)
            .withTime(VALID_TIME_TURKEY_SANDWICH)
            .withStep(VALID_STEP_TURKEY_SANDWICH).withGoals(VALID_GOAL_GRAIN).build();
    public static final Recipe FISH = new RecipeBuilder().withName(VALID_NAME_FISH).withTime(VALID_TIME_FISH)
            .withStep(VALID_STEP_FISH).withGoals(VALID_GOAL_PROTEIN).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecipes() {} // prevents instantiation

    /**
     * Returns an {@code RecipeBook} with all the typical recipes.
     */
    public static RecipeBook getTypicalRecipeBook() {
        RecipeBook ab = new RecipeBook();
        for (Recipe recipe : getTypicalRecipes()) {
            ab.addRecipe(recipe);
        }
        return ab;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(CAESAR_SALAD, GRILLED_SANDWICH, BOILED_CHICKEN, CHOCOLATE_CAKE,
                OMELET, STEAMED_EGG, TUNA_BREAD));
    }
}

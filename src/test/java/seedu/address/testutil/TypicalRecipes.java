package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_GRAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GOAL_PROTEIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_TURKEY_SANDWICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TURKEY_SANDWICH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STEP_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STEP_TURKEY_SANDWICH;
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
            .withTime("10")
            .withIngredients("Tomato, 100, vegetable", "Honeydew, 100, other")
            .withSteps("Cut tomatoes", "Remove honeydew skin")
            .withGoals("Herbivore").build();

    public static final Recipe GRILLED_SANDWICH = new RecipeBuilder().withName("Grilled Sandwich")
            .withTime("10")
            .withIngredients("Bread, 50, grain", "Cheese, 50, other")
            .withSteps("Spread butter on bread", "Heat pan to medium heat")
            .withGoals("Wholesome Wholemeal").build();

    public static final Recipe BOILED_CHICKEN = new RecipeBuilder().withName("Boiled Chicken")
            .withTime("10")
            .withIngredients("Chicken, 100, protein")
            .withSteps("Dice chicken")
            .withGoals("Bulk like the Hulk").build();

    public static final Recipe CHOCOLATE_CAKE = new RecipeBuilder().withName("Chocolate Cake")
            .withTime("120")
            .withIngredients("Sugar, 100, other")
            .withSteps("Caramelize sugar").build();

    public static final Recipe OMELET = new RecipeBuilder().withName("Omelet")
            .withTime("15")
            .withIngredients("Egg, 50, protein")
            .withSteps("Crack and beat eggs").build();

    public static final Recipe STEAMED_EGG = new RecipeBuilder().withName("Steamed Egg")
            .withTime("15")
            .withIngredients("Egg, 50, protein")
            .withSteps("Pour water into steamer and bring to a boil").build();

    public static final Recipe TUNA_BREAD = new RecipeBuilder().withName("Tuna Bread")
            .withTime("60")
            .withIngredients("Tuna, 100, protein", "Bread, 100, grain")
            .withGoals("Bulk like the Hulk", "Wholesome Wholemeal")
            .withSteps("Slice tuna").build();

    // Manually added
    public static final Recipe FISH_TACO = new RecipeBuilder().withName("Fish Taco").withTime("60")
            .withIngredients("Fish, 100, protein", "Taco, 50, grain")
            .withSteps("Grill fish until skin is slightly crispy and meat is opaque and flaky").build();
    public static final Recipe VEGETARIAN_PIZZA = new RecipeBuilder().withName("Vegetarian Pizza").withTime("45")
            .withIngredients("Tomato sauce, 100, other", "Dough, 300, grain", "Capsicum, 100, vegetable")
            .withGoals("Herbivore").withSteps("Roll dough until stretchy and thin").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe TURKEY_SANDWICH = new RecipeBuilder().withName(VALID_NAME_TURKEY_SANDWICH)
            .withTime(VALID_TIME_TURKEY_SANDWICH)
            .withIngredients(VALID_INGREDIENT_TURKEY_SANDWICH)
            .withGoals(VALID_GOAL_GRAIN).withSteps(VALID_STEP_TURKEY_SANDWICH).build();
    public static final Recipe FISH = new RecipeBuilder().withName(VALID_NAME_FISH).withTime(VALID_TIME_FISH)
            .withIngredients(VALID_INGREDIENT_FISH)
            .withGoals(VALID_GOAL_PROTEIN).withSteps(VALID_STEP_FISH).build();

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

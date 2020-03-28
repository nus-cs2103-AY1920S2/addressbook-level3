package seedu.recipe.testutil;

import static seedu.recipe.logic.commands.CommandTestUtil.VALID_FRUIT_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_FRUIT_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_GRAIN;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_PROTEIN;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GRAIN_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GRAIN_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_OTHER_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_OTHER_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PROTEIN_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PROTEIN_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_STEP_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_STEP_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_VEGE_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_VEGE_TURKEY_SANDWICH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe CAESAR_SALAD = new RecipeBuilder().withName("Caesar Salad")
            .withTime("10")
            .withVegetables("100g, Lettuce")
            .withSteps("Cut tomatoes", "Remove honeydew skin")
            .withGoals("Herbivore").build();

    public static final Recipe GRILLED_SANDWICH = new RecipeBuilder().withName("Grilled Sandwich")
            .withTime("10")
            .withGrains("50g, Bread")
            .withOthers("50g, Cheese")
            .withSteps("Spread butter on bread", "Heat pan to medium heat")
            .withGoals("Wholesome Wholemeal").build();

    public static final Recipe BOILED_CHICKEN = new RecipeBuilder().withName("Boiled Chicken")
            .withTime("10")
            .withProteins("100g, Chicken")
            .withSteps("Dice chicken")
            .withGoals("Bulk like the Hulk").build();

    public static final Recipe CHOCOLATE_CAKE = new RecipeBuilder().withName("Chocolate Cake")
            .withTime("120")
            .withOthers("100g, Sugar")
            .withSteps("Caramelize sugar").build();

    public static final Recipe OMELET = new RecipeBuilder().withName("Omelet")
            .withTime("15")
            .withProteins("50g, Egg")
            .withSteps("Crack and beat eggs").build();

    public static final Recipe STEAMED_EGG = new RecipeBuilder().withName("Steamed Egg")
            .withTime("15")
            .withProteins("50g, Egg")
            .withSteps("Pour water into steamer and bring to a boil").build();

    public static final Recipe TUNA_BREAD = new RecipeBuilder().withName("Tuna Bread")
            .withTime("60")
            .withGrains("100g, Bread")
            .withProteins("100g, Tuna")
            .withGoals("Bulk like the Hulk", "Wholesome Wholemeal")
            .withSteps("Slice tuna").build();

    // Manually added
    public static final Recipe FISH_TACO = new RecipeBuilder().withName("Fish Taco").withTime("60")
            .withGrains("50g, Taco")
            .withProteins("100g, Fish")
            .withSteps("Grill fish until skin is slightly crispy and meat is opaque and flaky").build();

    public static final Recipe VEGETARIAN_PIZZA = new RecipeBuilder().withName("Vegetarian Pizza").withTime("45")
            .withGrains("300g, Dough")
            .withVegetables("100g, Capsicum")
            .withOthers("100g, Tomato sauce")
            .withGoals("Herbivore").withSteps("Roll dough until stretchy and thin").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe TURKEY_SANDWICH = new RecipeBuilder().withName(VALID_NAME_TURKEY_SANDWICH)
            .withTime(VALID_TIME_TURKEY_SANDWICH)
            .withGrains(VALID_GRAIN_TURKEY_SANDWICH)
            .withVegetables(VALID_VEGE_TURKEY_SANDWICH)
            .withProteins(VALID_PROTEIN_TURKEY_SANDWICH)
            .withFruits(VALID_FRUIT_TURKEY_SANDWICH)
            .withOthers(VALID_OTHER_TURKEY_SANDWICH)
            .withGoals(VALID_GOAL_GRAIN).withSteps(VALID_STEP_TURKEY_SANDWICH).build();

    public static final Recipe FISH = new RecipeBuilder().withName(VALID_NAME_FISH).withTime(VALID_TIME_FISH)
            .withGrains(VALID_GRAIN_FISH)
            .withVegetables(VALID_VEGE_FISH)
            .withProteins(VALID_PROTEIN_FISH)
            .withFruits(VALID_FRUIT_FISH)
            .withOthers(VALID_OTHER_FISH)
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

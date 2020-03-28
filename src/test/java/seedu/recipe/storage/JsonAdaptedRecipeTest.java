package seedu.recipe.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recipe.storage.JsonAdaptedRecipe.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalRecipes.GRILLED_SANDWICH;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Time;
import seedu.recipe.model.recipe.ingredient.Ingredient;

public class JsonAdaptedRecipeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TIME = "+651234";
    private static final String INVALID_STEP = "";
    private static final String INVALID_GOAL = "#friend";
    private static final String VALID_NAME = GRILLED_SANDWICH.getName().toString();
    private static final String VALID_TIME = GRILLED_SANDWICH.getTime().toString();

    private static final List<JsonAdaptedGrain> VALID_GRAINS = GRILLED_SANDWICH.getGrains().stream()
            .map(JsonAdaptedGrain::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedVegetable> VALID_VEGETABLES = GRILLED_SANDWICH.getVegetables().stream()
            .map(JsonAdaptedVegetable::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProtein> VALID_PROTEINS = GRILLED_SANDWICH.getProteins().stream()
            .map(JsonAdaptedProtein::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedFruit> VALID_FRUITS = GRILLED_SANDWICH.getFruits().stream()
            .map(JsonAdaptedFruit::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedOther> VALID_OTHERS = GRILLED_SANDWICH.getOthers().stream()
            .map(JsonAdaptedOther::new)
            .collect(Collectors.toList());

    private static final List<JsonAdaptedStep> VALID_STEP = GRILLED_SANDWICH.getSteps().stream()
            .map(JsonAdaptedStep::new).collect(Collectors.toList());
    private static final List<JsonAdaptedGoal> VALID_GOALS = GRILLED_SANDWICH.getGoals().stream()
            .map(JsonAdaptedGoal::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedStep> INVALID_STEPS = new ArrayList<>(VALID_STEP);
    private static final List<JsonAdaptedGoal> INVALID_GOALS = new ArrayList<>(VALID_GOALS);
    private static final boolean IS_NOT_FAVOURITE = false;
    private static final boolean IS_FAVOURITE = true;

    @Test
    public void toModelType_validRecipeDetails_returnsRecipe() throws Exception {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(GRILLED_SANDWICH);
        assertEquals(GRILLED_SANDWICH, recipe.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(INVALID_NAME, VALID_TIME, IS_NOT_FAVOURITE,
                        VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                        VALID_STEP, VALID_GOALS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(null, VALID_TIME, IS_NOT_FAVOURITE,
                VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                VALID_STEP, VALID_GOALS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, INVALID_TIME, IS_NOT_FAVOURITE,
                        VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                        VALID_STEP, VALID_GOALS);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(VALID_NAME, null, IS_NOT_FAVOURITE,
                VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                VALID_STEP, VALID_GOALS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidStep_throwsIllegalValueException() {
        INVALID_STEPS.add(new JsonAdaptedStep(INVALID_STEP));
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_TIME, IS_NOT_FAVOURITE,
                        VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                        INVALID_STEPS, VALID_GOALS);
        String expectedMessage = Step.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullIngredient_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(VALID_NAME, VALID_TIME, IS_NOT_FAVOURITE,
                null, null, null, null, null,
                VALID_STEP, VALID_GOALS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Ingredient.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidGoals_throwsIllegalValueException() {
        INVALID_GOALS.add(new JsonAdaptedGoal(INVALID_GOAL));
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_NAME, VALID_TIME, IS_NOT_FAVOURITE,
                        VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                        VALID_STEP, INVALID_GOALS);
        assertThrows(IllegalValueException.class, recipe::toModelType);
    }
}

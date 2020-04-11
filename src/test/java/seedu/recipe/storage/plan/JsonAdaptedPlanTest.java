package seedu.recipe.storage.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.INVALID_NAME;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.IS_NOT_FAVOURITE;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_FRUITS;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_GOALS;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_GRAINS;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_NAME;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_OTHERS;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_PROTEINS;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_STEP;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_TIME;
import static seedu.recipe.storage.JsonAdaptedRecipeTest.VALID_VEGETABLES;
import static seedu.recipe.storage.plan.JsonAdaptedPlan.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalPlans.CAESAR_FUTURE_PLAN;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.storage.JsonAdaptedDate;
import seedu.recipe.storage.recipe.JsonAdaptedRecipe;

public class JsonAdaptedPlanTest {

    private static final String INVALID_DATE = "20200101";
    private static final String VALID_DATE = "2020-02-02";

    @Test
    public void toModelType_validPlanDetails_returnsPlan() throws Exception {
        JsonAdaptedPlan plan = new JsonAdaptedPlan(CAESAR_FUTURE_PLAN);
        assertEquals(CAESAR_FUTURE_PLAN, plan.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedRecipe validRecipe = new JsonAdaptedRecipe(VALID_NAME, VALID_TIME, IS_NOT_FAVOURITE,
                VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                VALID_STEP, VALID_GOALS);
        JsonAdaptedDate invalidDate = new JsonAdaptedDate(INVALID_DATE);
        JsonAdaptedPlan plan = new JsonAdaptedPlan(validRecipe, invalidDate);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, plan::toModelType);
    }

    @Test
    public void toModelType_invalidRecipe_throwsIllegalValueException() {
        JsonAdaptedRecipe invalidRecipe = new JsonAdaptedRecipe(INVALID_NAME, VALID_TIME, IS_NOT_FAVOURITE,
                VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                VALID_STEP, VALID_GOALS);
        JsonAdaptedDate validDate = new JsonAdaptedDate(VALID_DATE);
        JsonAdaptedPlan plan = new JsonAdaptedPlan(invalidRecipe, validDate);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, plan::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedRecipe validRecipe = new JsonAdaptedRecipe(VALID_NAME, VALID_TIME, IS_NOT_FAVOURITE,
                VALID_GRAINS, VALID_VEGETABLES, VALID_PROTEINS, VALID_FRUITS, VALID_OTHERS,
                VALID_STEP, VALID_GOALS);
        JsonAdaptedPlan plan = new JsonAdaptedPlan(validRecipe, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, plan::toModelType);
    }

    @Test
    public void toModelType_nullRecipe_throwsIllegalValueException() {
        JsonAdaptedDate validDate = new JsonAdaptedDate(VALID_DATE);
        JsonAdaptedPlan plan = new JsonAdaptedPlan(null, validDate);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Recipe.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, plan::toModelType);
    }

}

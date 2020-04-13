package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedRecipe.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.APPLE;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.Step;

public class JsonAdaptedRecipeTest {
    private static final String INVALID_INGREDIENT_NAME = "r!ce"; // contains invalid symbol
    private static final String INVALID_INGREDIENT_QUANTITY = "one"; // starts with alphabets
    private static final String INVALID_RECIPE_DESCRIPTION = " "; // spaces only
    private static final String INVALID_RECIPE_NAME = " "; // spaces only
    private static final String INVALID_STEP_DESCRIPTION = " "; // spaces only
    private static final String INVALID_TAG = "#friend"; // contains symbol

    private static final String VALID_INGREDIENT_NAME = APPLE.getName().toString();
    private static final String VALID_INGREDIENT_QUANTITY = APPLE.getQuantity().toString();
    private static final List<JsonAdaptedIngredient> VALID_INGREDIENTS = CARBONARA.getIngredients().stream()
            .map(JsonAdaptedIngredient::new)
            .collect(Collectors.toList());
    private static final String VALID_RECIPE_NAME = CARBONARA.getName().toString();
    private static final String VALID_RECIPE_DESCRIPTION = CARBONARA.getDescription().toString();
    private static final List<JsonAdaptedStep> VALID_STEPS = CARBONARA.getSteps()
            .asUnmodifiableObservableList().stream()
            .map(JsonAdaptedStep::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS = CARBONARA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRecipeDetails_returnsRecipe() throws Exception {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(CARBONARA);
        assertEquals(CARBONARA, recipe.toModelType());
    }

    @Test
    public void toModelType_invalidRecipeName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(INVALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION,
                        VALID_INGREDIENTS, VALID_STEPS, VALID_TAGS);
        String expectedMessage = RecipeName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullRecipeName_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(null, VALID_RECIPE_DESCRIPTION,
                VALID_INGREDIENTS, VALID_STEPS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RecipeName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidRecipeDescription_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_RECIPE_NAME, INVALID_RECIPE_DESCRIPTION,
                        VALID_INGREDIENTS, VALID_STEPS, VALID_TAGS);
        String expectedMessage = RecipeDescription.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullRecipeDescription_throwsIllegalValueException() {
        JsonAdaptedRecipe recipe = new JsonAdaptedRecipe(VALID_RECIPE_NAME, null,
                VALID_INGREDIENTS, VALID_STEPS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RecipeDescription.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidRecipeIngredientName_throwsIllegalValueException() {
        List<JsonAdaptedIngredient> invalidIngredients = new ArrayList<>(VALID_INGREDIENTS);
        invalidIngredients.add(new JsonAdaptedIngredient(INVALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY));

        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION,
                        invalidIngredients, VALID_STEPS, VALID_TAGS);
        String expectedMessage = IngredientName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidRecipeIngredientQuantity_throwsIllegalValueException() {
        List<JsonAdaptedIngredient> invalidIngredients = new ArrayList<>(VALID_INGREDIENTS);
        invalidIngredients.add(new JsonAdaptedIngredient(VALID_INGREDIENT_NAME, INVALID_INGREDIENT_QUANTITY));

        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION,
                        invalidIngredients, VALID_STEPS, VALID_TAGS);
        String expectedMessage = IngredientQuantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidRecipeSteps_throwsIllegalValueException() {
        List<JsonAdaptedStep> invalidSteps = new ArrayList<>(VALID_STEPS);
        invalidSteps.add(new JsonAdaptedStep(INVALID_STEP_DESCRIPTION));

        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION,
                        VALID_INGREDIENTS, invalidSteps, VALID_TAGS);
        String expectedMessage = Step.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedRecipe recipe =
                new JsonAdaptedRecipe(VALID_RECIPE_NAME, VALID_RECIPE_DESCRIPTION,
                        VALID_INGREDIENTS, VALID_STEPS, invalidTags);
        assertThrows(IllegalValueException.class, recipe::toModelType);
    }
}

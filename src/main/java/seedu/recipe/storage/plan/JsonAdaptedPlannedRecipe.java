package seedu.recipe.storage.plan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.storage.JsonAdaptedRecipe;

/**
 * Jackson-friendly version of {@link PlannedRecipe}.
 */
public class JsonAdaptedPlannedRecipe {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Planned recipe's %s field is missing!";

    private final JsonAdaptedRecipe recipe;
    private final JsonAdaptedPlannedDate date;

    /**
     * Constructs a {@code JsonAdaptedPlannedRecipe} with the given {@code PlannedRecipe}.
     */
    @JsonCreator
    public JsonAdaptedPlannedRecipe(@JsonProperty("recipe") JsonAdaptedRecipe recipe,
            @JsonProperty("date") JsonAdaptedPlannedDate date) {
        this.recipe = recipe;
        this.date = date;
    }

    /**
     * Converts a given {@code plannedRecipe} into this class for Jackson use.
     */
    public JsonAdaptedPlannedRecipe(PlannedRecipe plannedRecipe) {
        recipe = new JsonAdaptedRecipe(plannedRecipe.getRecipes());
        date = new JsonAdaptedPlannedDate(plannedRecipe.getDate().toStringForJson());
    }

    /**
     * Converts this Jackson-friendly adapted PlannedRecipe object into the model's {@code PlannedRecipe} object.
     * @return PlannedRecipe object that the adapted PlannedRecipe was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted PlannedRecipe.
     */
    public PlannedRecipe toModelType() throws IllegalValueException {
        Recipe modelRecipe = recipe.toModelType();
        PlannedDate modelPlannedDate = date.toModelType();
        return new PlannedRecipe(modelRecipe, modelPlannedDate);
    }


}

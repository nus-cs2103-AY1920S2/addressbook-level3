package seedu.recipe.storage.plan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.storage.JsonAdaptedDate;
import seedu.recipe.storage.recipe.JsonAdaptedRecipe;

/**
 * Jackson-friendly version of {@link Plan}.
 */
public class JsonAdaptedPlan {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Planned recipe's %s field is missing!";

    private final JsonAdaptedRecipe recipe;
    private final JsonAdaptedDate date;

    /**
     * Constructs a {@code JsonAdaptedPlan} with the given {@code Plan}.
     */
    @JsonCreator
    public JsonAdaptedPlan(@JsonProperty("recipe") JsonAdaptedRecipe recipe,
                           @JsonProperty("date") JsonAdaptedDate date) {
        this.recipe = recipe;
        this.date = date;
    }

    /**
     * Converts a given {@code source} into this class for Jackson use.
     */
    public JsonAdaptedPlan(Plan source) {
        recipe = new JsonAdaptedRecipe(source.getRecipe());
        date = new JsonAdaptedDate(source.getDate().toStringForJson());
    }

    /**
     * Converts this Jackson-friendly adapted Plan object into the model's {@code Plan} object.
     * @return Plan object that the adapted plan was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Plan.
     */
    public Plan toModelType() throws IllegalValueException {
        if (recipe == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Recipe.class.getSimpleName()));
        }
        final Recipe modelRecipe = recipe.toModelType();

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelPlannedDate = date.toModelType();

        return new Plan(modelRecipe, modelPlannedDate);
    }


}

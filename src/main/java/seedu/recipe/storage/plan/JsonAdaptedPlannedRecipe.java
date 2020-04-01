package seedu.recipe.storage.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.storage.JsonAdaptedRecipe;

/**
 * Jackson-friendly version of {@link PlannedRecipe}.
 */
public class JsonAdaptedPlannedRecipe {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Planned recipe's %s field is missing!";

    private final List<JsonAdaptedRecipe> recipes = new ArrayList<>();
    private final JsonAdaptedPlannedDate date;

    /**
     * Constructs a {@code JsonAdaptedPlannedRecipe} with the given {@code PlannedRecipe}.
     */
    @JsonCreator
    public JsonAdaptedPlannedRecipe(@JsonProperty("recipes") List<JsonAdaptedRecipe> recipes,
            @JsonProperty("date") JsonAdaptedPlannedDate date) {
        if (recipes != null) {
            this.recipes.addAll(recipes);
        }
        this.date = date;
    }

    /**
     * Converts a given {@code plannedRecipe} into this class for Jackson use.
     */
    public JsonAdaptedPlannedRecipe(PlannedRecipe plannedRecipe) {
        recipes.addAll(plannedRecipe.getRecipes()
                .stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        date = new JsonAdaptedPlannedDate(plannedRecipe.getDate().toStringForJson());
    }

    /**
     * Converts this Jackson-friendly adapted PlannedRecipe object into the model's {@code PlannedRecipe} object.
     * @return PlannedRecipe object that the adapted PlannedRecipe was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted PlannedRecipe.
     */
    public PlannedRecipe toModelType() throws IllegalValueException {

        final List<Recipe> modelRecipe = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes) {
            modelRecipe.add(recipe.toModelType());
        }
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelPlannedDate = date.toModelType();

        return new PlannedRecipe(modelRecipe, modelPlannedDate);
    }


}

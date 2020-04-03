package seedu.recipe.storage.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.storage.JsonAdaptedDate;
import seedu.recipe.storage.JsonAdaptedRecipe;

/**
 * Jackson-friendly version of {@link PlannedDate}.
 */
public class JsonAdaptedPlannedDate {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Planned recipe's %s field is missing!";

    private final List<JsonAdaptedRecipe> recipes = new ArrayList<>();
    private final JsonAdaptedDate date;

    /**
     * Constructs a {@code JsonAdaptedPlannedDate} with the given {@code PlannedDate}.
     */
    @JsonCreator
    public JsonAdaptedPlannedDate(@JsonProperty("recipes") List<JsonAdaptedRecipe> recipes,
                                  @JsonProperty("date") JsonAdaptedDate date) {
        if (recipes != null) {
            this.recipes.addAll(recipes);
        }
        this.date = date;
    }

    /**
     * Converts a given {@code plannedDate} into this class for Jackson use.
     */
    public JsonAdaptedPlannedDate(PlannedDate plannedDate) {
        recipes.addAll(plannedDate.getRecipes()
                .stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
        date = new JsonAdaptedDate(plannedDate.getDate().toStringForJson());
    }

    /**
     * Converts this Jackson-friendly adapted PlannedDate object into the model's {@code PlannedDate} object.
     * @return PlannedDate object that the adapted PlannedDate was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted PlannedDate.
     */
    public PlannedDate toModelType() throws IllegalValueException {

        final List<Recipe> modelRecipe = new ArrayList<>();
        for (JsonAdaptedRecipe recipe : recipes) {
            modelRecipe.add(recipe.toModelType());
        }
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelPlannedDate = date.toModelType();

        return new PlannedDate(modelRecipe, modelPlannedDate);
    }


}

package seedu.recipe.storage.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.ReadOnlyPlannedBook;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * An Immutable PlannedBook that is serializable to JSON format.
 */
@JsonRootName(value = "plannedbook")
class JsonSerializablePlannedBook {

    public static final String MESSAGE_DUPLICATE_PLANNED_RECIPE = "Planned recipes list contains duplicate"
            + "planned recipe(s).";

    private final List<JsonAdaptedPlan> plannedRecipes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePlannedBook} with the given recipes.
     */
    @JsonCreator
    public JsonSerializablePlannedBook(@JsonProperty("plannedRecipes") List<JsonAdaptedPlan> plannedRecipes) {
        this.plannedRecipes.addAll(plannedRecipes);
    }

    /**
     * Converts a given {@code ReadOnlyPlannedBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePlannedBook}.
     */
    public JsonSerializablePlannedBook(ReadOnlyPlannedBook source) {
        plannedRecipes.addAll(source.getPlannedList().stream().map(JsonAdaptedPlan::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this planned recipe book into the model's {@code PlannedBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PlannedBook toModelType() throws IllegalValueException {
        PlannedBook plannedBook = new PlannedBook();
        for (JsonAdaptedPlan jsonAdaptedPlan : plannedRecipes) {
            Plan plan = jsonAdaptedPlan.toModelType();
            if (plannedBook.containsPlan(plan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PLANNED_RECIPE);
            }
            Recipe recipe = plan.getRecipe();
            plannedBook.addPlan(recipe, plan);
        }
        return plannedBook;
    }

}

package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;
import seedu.address.model.recipe.Time;

import seedu.address.model.recipe.ingredient.Ingredient;

/**
 * Jackson-friendly version of {@link Recipe}.
 */
class JsonAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    private final String name;
    private final String time;
    private final boolean isFavourite;
    private final List<JsonAdaptedStep> steps = new ArrayList<>();
    private final List<JsonAdaptedGoal> goals = new ArrayList<>();
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("name") String name, @JsonProperty("time") String time,
            @JsonProperty("isFavourite") boolean isFavourite,
            @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
            @JsonProperty("steps") List<JsonAdaptedStep> steps,
            @JsonProperty("goals") List<JsonAdaptedGoal> goals) {
        this.name = name;
        this.time = time;
        this.isFavourite = isFavourite;
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
        if (steps != null) {
            this.steps.addAll(steps);
        }
        if (goals != null) {
            this.goals.addAll(goals);
        }
    }

    /**
     * Converts a given {@code Recipe} into this class for Jackson use.
     */
    public JsonAdaptedRecipe(Recipe source) {
        name = source.getName().fullName;
        time = source.getTime().value;
        isFavourite = source.getFavouriteStatus();
        ingredients.addAll(source.getIngredients().stream()
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList()));
        steps.addAll(source.getSteps().stream()
                .map(JsonAdaptedStep::new).collect(Collectors.toList()));
        goals.addAll(source.getGoals().stream()
                .map(JsonAdaptedGoal::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted recipe object into the model's {@code Recipe} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe.
     */
    public Recipe toModelType() throws IllegalValueException {
        final List<Goal> recipeGoals = new ArrayList<>();
        for (JsonAdaptedGoal goal : goals) {
            recipeGoals.add(goal.toModelType());
        }

        final List<Ingredient> recipeIngredients = new ArrayList<>();
        for (JsonAdaptedIngredient ingredient : ingredients) {
            recipeIngredients.add(ingredient.toModelType());
        }

        final List<Step> recipeSteps = new ArrayList<>();
        if (steps.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Step.class.getSimpleName()));
        }
        for (JsonAdaptedStep step : steps) {
            recipeSteps.add(step.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        final Set<Goal> modelGoals = new HashSet<>(recipeGoals);
        final Set<Ingredient> modelIngredients = new TreeSet<>(recipeIngredients);
        final ArrayList<Step> modelSteps = new ArrayList<>(recipeSteps);
        return new Recipe(modelName, modelTime, modelIngredients, modelSteps, modelGoals, isFavourite);
    }

}

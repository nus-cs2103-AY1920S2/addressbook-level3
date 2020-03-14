package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.goal.Goal;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.recipe.Email;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Time;

/**
 * Jackson-friendly version of {@link Recipe}.
 */
class JsonAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    private final String name;
    private final String time;
    private final String email;
    private final List<JsonAdaptedGoal> goals = new ArrayList<>();
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("name") String name, @JsonProperty("time") String time,
            @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
            @JsonProperty("email") String email,
            @JsonProperty("goals") List<JsonAdaptedGoal> goals) {
        this.name = name;
        this.time = time;
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
        this.email = email;
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
        ingredients.addAll(source.getIngredients().stream()
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList()));
        email = source.getEmail().value;
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

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Set<Goal> modelGoals = new HashSet<>(recipeGoals);
        final Set<Ingredient> modelIngredients = new HashSet<>(recipeIngredients);
        return new Recipe(modelName, modelTime, modelEmail, modelGoals, modelIngredients);
    }

}

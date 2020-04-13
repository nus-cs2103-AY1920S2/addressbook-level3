package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.step.Step;
import seedu.address.model.step.UniqueStepList;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Recipe}.
 */
class JsonAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Recipe contains duplicate ingredient(s).";
    public static final String MESSAGE_DUPLICATE_STEP = "Recipe contains duplicate step(s).";

    private final String name;
    private final String description;
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();
    private final List<JsonAdaptedStep> steps = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("name") String name,
                             @JsonProperty("description") String description,
                             @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
                             @JsonProperty("steps") List<JsonAdaptedStep> steps,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.description = description;
        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
        if (steps != null) {
            this.steps.addAll(steps);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Recipe} into this class for Jackson use.
     */
    public JsonAdaptedRecipe(Recipe source) {
        name = source.getName().fullRecipeName;
        description = source.getDescription().fullRecipeDescription;
        ingredients.addAll(source.getIngredients().stream()
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList()));
        steps.addAll(source.getSteps().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedStep::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted recipe object into the model's {@code Recipe} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe.
     */
    public Recipe toModelType() throws IllegalValueException {
        final List<Tag> recipeTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            recipeTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, RecipeName.class.getSimpleName()));
        }
        if (!RecipeName.isValidRecipeName(name)) {
            throw new IllegalValueException(RecipeName.MESSAGE_CONSTRAINTS);
        }

        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, RecipeDescription.class.getSimpleName()));
        }
        if (!RecipeDescription.isValidRecipeDescription(description)) {
            throw new IllegalValueException(RecipeDescription.MESSAGE_CONSTRAINTS);
        }

        final RecipeName modelName = new RecipeName(name);
        final RecipeDescription modelDescription = new RecipeDescription(description);
        final UniqueIngredientList modelIngredientList = getModelIngredientList();
        final UniqueStepList modelStepList = getModelStepList();
        final Set<Tag> modelTags = new HashSet<>(recipeTags);

        return new Recipe(modelName, modelDescription, modelIngredientList, modelStepList, modelTags);
    }

    /**
    * Converts Jackson-friendly adapted list of Ingredient objects into the model's {@code UniqueIngredientList} object.
    *
    * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient list.
    */
    private UniqueIngredientList getModelIngredientList() throws IllegalValueException {
        final UniqueIngredientList modelIngredientList = new UniqueIngredientList();
        for (JsonAdaptedIngredient jsonAdaptedIngredient : ingredients) {
            Ingredient ingredient = jsonAdaptedIngredient.toModelType();
            if (modelIngredientList.contains(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            modelIngredientList.add(ingredient);
        }
        return modelIngredientList;
    }

    /**
     * Converts Jackson-friendly adapted list of Step objects into the model's {@code UniqueStepList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted step list.
     */
    private UniqueStepList getModelStepList() throws IllegalValueException {
        final UniqueStepList modelStepList = new UniqueStepList();
        for (JsonAdaptedStep jsonAdaptedStep : steps) {
            Step step = jsonAdaptedStep.toModelType();
            if (modelStepList.contains(step)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STEP);
            }
            modelStepList.add(step);
        }
        return modelStepList;
    }

}

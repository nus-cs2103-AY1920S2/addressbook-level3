package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.recipe.Calorie;
import seedu.address.model.recipe.IngredientList;
import seedu.address.model.recipe.InstructionList;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Recipe}.
 */
class JsonAdaptedRecipe {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    private final String name;
    private final String ingredients;
    private final String instructions;
    private final String calorie;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRecipe} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecipe(@JsonProperty("name") String name, @JsonProperty("ingredients") String ingredients,
                             @JsonProperty("instructions") String instructions,
                             @JsonProperty("calorie") String calorie,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.calorie = calorie;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Recipe} into this class for Jackson use.
     */
    public JsonAdaptedRecipe(Recipe source) {
        name = source.getName().name;
        ingredients = source.getIngredients().ingredientListString;
        instructions = source.getInstructions().instructionListString;
        calorie = source.getCalorie().calorie;
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (ingredients == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, IngredientList.class.getSimpleName()));
        }
        if (!IngredientList.isValidIngredients(ingredients)) {
            throw new IllegalValueException(IngredientList.MESSAGE_CONSTRAINTS);
        }
        final IngredientList modelIngredients = new IngredientList(ingredients);

        if (instructions == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InstructionList.class.getSimpleName()));
        }
        if (!InstructionList.isValidInstructions(instructions)) {
            throw new IllegalValueException(InstructionList.MESSAGE_CONSTRAINTS);
        }
        final InstructionList modelInstructions = new InstructionList(instructions);

        final Calorie modelCalorie = new Calorie(calorie);
        final Set<Tag> modelTags = new HashSet<>(recipeTags);
        return new Recipe(modelName, modelIngredients, modelInstructions, modelCalorie, modelTags);
    }

}

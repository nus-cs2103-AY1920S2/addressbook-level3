package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Cookbook;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.recipe.Recipe;

/**
 * An Immutable Cookbook that is serializable to JSON format.
 */
@JsonRootName(value = "cookbook")
class JsonSerializableCookbook {

    public static final String MESSAGE_DUPLICATE_RECIPE = "Recipe list contains duplicate recipe(s).";

    private final List<JsonAdaptedRecipe> recipes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCookbook} with the given recipes.
     */
    @JsonCreator
    public JsonSerializableCookbook(@JsonProperty("recipes") List<JsonAdaptedRecipe> recipes) {
        this.recipes.addAll(recipes);
    }

    /**
     * Converts a given {@code ReadOnlyCookbook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCookbook}.
     */
    public JsonSerializableCookbook(ReadOnlyCookbook source) {
        recipes.addAll(source.getRecipeList()
                .stream()
                .map(JsonAdaptedRecipe::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this cookbook into the model's {@code Cookbook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Cookbook toModelType() throws IllegalValueException {
        Cookbook cookbook = new Cookbook();
        for (JsonAdaptedRecipe jsonAdaptedRecipe : recipes) {
            Recipe recipe = jsonAdaptedRecipe.toModelType();
            if (cookbook.hasRecipe(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECIPE);
            }
            cookbook.addRecipe(recipe);
        }
        return cookbook;
    }

}

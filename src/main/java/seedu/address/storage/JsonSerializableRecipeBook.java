package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.Recipe;

/**
 * An Immutable RecipeBook that is serializable to JSON format.
 */
@JsonRootName(value = "recipeBook")
class JsonSerializableRecipeBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate recipe(s).";

    private final List<JsonAdaptedRecipe> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRecipeBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableRecipeBook(@JsonProperty("persons") List<JsonAdaptedRecipe> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyRecipeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRecipeBook}.
     */
    public JsonSerializableRecipeBook(ReadOnlyRecipeBook source) {
        persons.addAll(source.getRecipeList().stream().map(JsonAdaptedRecipe::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code RecipeBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RecipeBook toModelType() throws IllegalValueException {
        RecipeBook recipeBook = new RecipeBook();
        for (JsonAdaptedRecipe jsonAdaptedRecipe : persons) {
            Recipe recipe = jsonAdaptedRecipe.toModelType();
            if (recipeBook.hasRecipe(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            recipeBook.addRecipe(recipe);
        }
        return recipeBook;
    }

}

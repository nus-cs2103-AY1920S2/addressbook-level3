package seedu.recipe.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.ReadOnlyCookedRecord;
import seedu.recipe.model.cooked.CookedRecord;

/**
 * An Immutable CookedRecord that is serializable to JSON format.
 */
@JsonRootName(value = "cookedrecord")
class JsonSerializableCookedRecord {

    public static final String MESSAGE_COOKED_RECIPE = "Recipe has already been cooked.";

    private final List<JsonAdaptedRecipe> cooked = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCookedRecord} with the given records.
     */
    @JsonCreator
    public JsonSerializableCookedRecord(@JsonProperty("cooked") List<JsonAdaptedRecipe> recipes) {
        this.cooked.addAll(recipes);
    }

    /**
     * Converts a given {@code ReadOnlyRecipeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRecipeBook}.
     */
    public JsonSerializableCookedRecord(ReadOnlyCookedRecord source) {
        cooked.addAll(source.getcookedRecordList().stream()
                .map(JsonAdaptedCookedRecord::new).collect(Collectors.toList()));
    }

    /**
     * Converts this recipe book into the model's {@code RecipeBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CookedRecord toModelType() throws IllegalValueException {
        CookedRecord cookedRecord = new CookedRecord();
        for (JsonAdaptedCookedRecord jsonAdaptedCookedRecord : cooked) {
            CookedRecord record = jsonAdaptedCookedRecord.toModelType();
            if (cookedRecord.hasBeenCooked(record)) {
                throw new IllegalValueException(MESSAGE_COOKED_RECIPE);
            }
            cookedRecord.addRecord(record);
        }
        return cookedRecord;
    }

}


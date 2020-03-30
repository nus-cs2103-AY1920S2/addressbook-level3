package seedu.recipe.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.cooked.CookedRecordBook;

/**
 * An Immutable CookedRecordBook that is serializable to JSON format.
 */
@JsonRootName(value = "cookedrecord")
class JsonSerializableCookedRecordBook {

    public static final String MESSAGE_COOKED_RECIPE = "Recipe has already been cooked.";

    private final List<JsonAdaptedRecipe> records = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCookedRecordBook} with the given records.
     */
    @JsonCreator
    public JsonSerializableCookedRecordBook(@JsonProperty("records") List<JsonAdaptedRecipe> recipes) {
        this.records.addAll(recipes);
    }

    public CookedRecordBook toModelType() {
        return new CookedRecordBook();
    }

    /**
     * Converts a given {@code ReadOnlyRecipeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRecipeBook}.
     */
    /*
    public JsonSerializableCookedRecordBook(ReadOnlyCookedRecordBook source) {
        records.addAll(source.getRecordsList().stream()
                .map(JsonAdaptedRecord::new).collect(Collectors.toList()));
    }
*/
    /**
     * Converts this recipe book into the model's {@code RecipeBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    /*
    public CookedRecordBook toModelType() throws IllegalValueException {
        CookedRecordBook cookedRecord = new CookedRecordBook();
        for (JsonAdaptedRecord jsonAdaptedRecord : records) {
            CookedRecordBook record = jsonAdaptedRecord.toModelType();
            if (cookedRecord.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_COOKED_RECIPE);
            }
            cookedRecord.addRecord(record);
        }
        return cookedRecord;
    }*/

}


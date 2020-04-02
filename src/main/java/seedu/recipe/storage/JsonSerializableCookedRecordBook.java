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
import seedu.recipe.model.cooked.Record;

/**
 * An Immutable CookedRecordBook that is serializable to JSON format.
 */
@JsonRootName(value = "cookedrecord")
class JsonSerializableCookedRecordBook {

    public static final String MESSAGE_COOKED_RECIPE = "Recipe has already been cooked.";

    private final List<JsonAdaptedRecord> records = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCookedRecordBook} with the given records.
     */
    @JsonCreator
    public JsonSerializableCookedRecordBook(@JsonProperty("records") List<JsonAdaptedRecord> records) {
        this.records.addAll(records);
    }

    /**
     * Converts a given {@code ReadOnlyCookedRecordBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCookedRecordBook}.
     */

    public JsonSerializableCookedRecordBook(ReadOnlyCookedRecordBook source) {
        records.addAll(source.getRecordsList().stream()
                .map(JsonAdaptedRecord::new).collect(Collectors.toList()));
    }

    /**
     * Converts this record book into the model's {@code CookedRecordBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */

    public CookedRecordBook toModelType() throws IllegalValueException {
        CookedRecordBook cookedRecord = new CookedRecordBook();
        for (JsonAdaptedRecord jsonAdaptedRecord : records) {
            Record record = jsonAdaptedRecord.toModelType();
            if (cookedRecord.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_COOKED_RECIPE);
            }
            cookedRecord.addRecord(record);
        }
        return cookedRecord;
    }

}


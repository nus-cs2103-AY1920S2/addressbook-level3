package seedu.recipe.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.recipe.Name;

/**
 * Jackson-friendly version of {@link Record}.
 */
class JsonAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    private final String name;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedRecord} with the given recipe details.
     */
    @JsonCreator
    public JsonAdaptedRecord(@JsonProperty("name") String name, @JsonProperty("date") String date) {
        this.name = name;
        this.date = date;
    }

    /**
     * Converts a given {@code Record} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record source) {
        name = source.getName().fullName;
        date = source.getDate().toStringForJson();

    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Record} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recipe.
     */
    public Record toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        final Date modelDate = new Date(date);

        return new Record(modelName, modelDate);
    }

}

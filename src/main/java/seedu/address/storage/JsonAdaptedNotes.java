package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Notes;

/**
 * Jackson-friendly version of {@link Notes}.
 */
public class JsonAdaptedNotes {
    private final String notes;

    /**
     * Constructs a {@code JsonAdaptedNotes} with the given {@code notes}.
     */
    @JsonCreator
    public JsonAdaptedNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Converts a given {@code Notes} into this class for Jackson use.
     */
    public JsonAdaptedNotes(Notes source) {
        notes = source.note;
    }

    @JsonValue
    public String getNotes() {
        return notes;
    }

    /**
     * Converts this Jackson-friendly adapted notes object into the model's {@code Notes} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted remark.
     */
    public Notes toModelType() {
        return new Notes(notes);
    }

}

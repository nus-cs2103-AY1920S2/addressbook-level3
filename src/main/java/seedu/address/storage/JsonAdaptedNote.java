package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Note;

/**
 * Jackson-friendly version of {@link Note}.
 */
public class JsonAdaptedNote {
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedNote} with the given {@code note}.
     */
    @JsonCreator
    public JsonAdaptedNote(String note) {
        this.note = note;
    }

    /**
     * Converts a given {@code Notes} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        note = source.note;
    }

    @JsonValue
    public String getNote() {
        return note;
    }

    /**
     * Converts this Jackson-friendly adapted notes object into the model's {@code Notes} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted remark.
     */
    public Note toModelType() {
        return new Note(note);
    }

}

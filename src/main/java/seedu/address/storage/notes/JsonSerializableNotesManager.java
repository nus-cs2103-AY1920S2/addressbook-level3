package seedu.address.storage.notes;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_NOTES;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.notes.Notes;
import seedu.address.model.notes.NotesManager;
import seedu.address.model.notes.ReadOnlyNotes;


/**
 * Immutable NotesManager that is serializable to JSON format.
 */

@JsonRootName(value = "notesManager")
public class JsonSerializableNotesManager {

    private final List<JsonAdaptedNotes> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNotesManager} with the given notes.
     * @param notes
     */
    @JsonCreator
    public JsonSerializableNotesManager(@JsonProperty("notes") List<JsonAdaptedNotes> notes) {
        this.notes.addAll(notes);
    }

    /**
     * Converts a given {@code ReadOnlyNoes} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableNotesManager}.
     */

    public JsonSerializableNotesManager(ReadOnlyNotes source) {
        notes.addAll(source.getNotesList().stream().map(JsonAdaptedNotes::new).collect(Collectors.toList()));
    }

    /**
     * Converts notes records into the model's {@code NotesManager} object.
     * @return
     * @throws IllegalValueException
     */
    public NotesManager toModelType() throws IllegalValueException {
        NotesManager notesManager = new NotesManager();
        for (JsonAdaptedNotes jsonAdaptedNotes : notes) {
            Notes note = jsonAdaptedNotes.toModelType();
            if (notesManager.hasNote(note)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_NOTES);
            }
            notesManager.addNote(note);
        }
        return notesManager;
    }


}

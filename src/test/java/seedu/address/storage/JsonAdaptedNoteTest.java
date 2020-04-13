package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_IS_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BACKEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.storage.JsonAdaptedNote.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.testutil.NoteBuilder;
import seedu.address.testutil.TypicalNote;

public class JsonAdaptedNoteTest {
    @Test
    public void toModelType_validNote_returnsNote() throws IllegalValueException {
        // Without tags
        JsonAdaptedNote jsonAdaptedNote = new JsonAdaptedNote(new NoteBuilder(TypicalNote.FINISH_CS_2103).build());
        assertEquals(new NoteBuilder(TypicalNote.FINISH_CS_2103).build(), jsonAdaptedNote.toModelType());

        jsonAdaptedNote = new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                "1", VALID_NOTE_TIME, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertEquals(new NoteBuilder(TypicalNote.FINISH_CS_2103).withTags().build(), jsonAdaptedNote.toModelType());

        // With tags
        jsonAdaptedNote = new JsonAdaptedNote(
                new NoteBuilder(TypicalNote.FINISH_CS_2103).withTags(VALID_TAG_TECH, VALID_TAG_BACKEND).build());
        assertEquals(
                new NoteBuilder(TypicalNote.FINISH_CS_2103).withTags(VALID_TAG_TECH, VALID_TAG_BACKEND).build(),
                jsonAdaptedNote.toModelType());

        List<JsonAdaptedTag> tagList = new ArrayList<>();
        tagList.add(new JsonAdaptedTag(VALID_TAG_BACKEND));
        tagList.add(new JsonAdaptedTag(VALID_TAG_TECH));
        jsonAdaptedNote = new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                "1", VALID_NOTE_TIME, VALID_NOTE_IS_DONE, tagList);
        assertEquals(
                new NoteBuilder(TypicalNote.FINISH_CS_2103).withTags(VALID_TAG_BACKEND, VALID_TAG_TECH).build(),
                jsonAdaptedNote.toModelType());
    }

    @Test
    public void toModelType_missingName_throwsIllegalValueException() {
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(null,
                        "1", VALID_NOTE_TIME, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()),
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(INVALID_NAME_DESC,
                        "1", VALID_NOTE_TIME, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                Name.MESSAGE_CONSTRAINTS,
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void toModelType_missingTime_throwsIllegalValueException() {
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        "1", null, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()),
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        "1", INVALID_TO_DESC, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                Time.MESSAGE_CONSTRAINTS,
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void toModelType_missingDone_throwsIllegalValueException() {
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        "1", VALID_NOTE_TIME, null, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Done"),
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void toModelType_invalidDone_throwsIllegalValueException() {
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        "1", VALID_NOTE_TIME, "tru", new ArrayList<>());
        assertThrows(IllegalValueException.class,
                "A boolean field can only be true or false.",
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void toModelType_missingId_throwsIllegalValueException() {
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        null, VALID_NOTE_TIME, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"),
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        // Id must be an integer
        JsonAdaptedNote jsonAdaptedNote =
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        "a", VALID_NOTE_TIME, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                "The id field can only be an integer.",
                jsonAdaptedNote::toModelType);

        // Id cannot be negative
        jsonAdaptedNote =
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        "-1", VALID_NOTE_TIME, VALID_NOTE_IS_DONE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                "The id field must not be negative.",
                jsonAdaptedNote::toModelType);
    }

    @Test
    public void equals() {
        JsonAdaptedNote jsonAdaptedCS2103 = new JsonAdaptedNote(new NoteBuilder(TypicalNote.FINISH_CS_2103).build());
        JsonAdaptedNote jsonAdaptedHw = new JsonAdaptedNote(new NoteBuilder(TypicalNote.FINISH_HOMEWORK).build());

        List<JsonAdaptedTag> tags = new ArrayList<>();
        tags.add(new JsonAdaptedTag(VALID_TAG_URGENT));

        // Two constructors gives the same result
        assertEquals(jsonAdaptedCS2103,
                new JsonAdaptedNote(VALID_NOTE_NAME_CS2103,
                        "1", VALID_NOTE_TIME, VALID_NOTE_IS_DONE, tags));

        // Different json adapted notes are not equal
        assertNotEquals(jsonAdaptedCS2103, jsonAdaptedHw);
    }

}

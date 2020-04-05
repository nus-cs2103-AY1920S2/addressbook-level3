package seedu.address.storage.notes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.notes.Notes;

/**
 * Constructs a JsonAdaptedNotes with given details
 */
public class JsonAdaptedNotes {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Notes in incorrect format.";

    private final String student;
    private final String content;
    private final String priority;
    private final String dateTime;

    /**
     * Constructs a {@code JsonAdaptedNotes} with the given note details.
     * @param student
     * @param content
     * @param priority
     * @param dateTime
     */
    @JsonCreator
    public JsonAdaptedNotes(@JsonProperty("student") String student, @JsonProperty("content") String content,
                            @JsonProperty("priority") String priority, @JsonProperty("dateTime") String dateTime) {
        this.student = student;
        this.content = content;
        this.priority = priority;
        this.dateTime = dateTime;
    }

    /**
     * Converts a given {@code source} into this class for Jackson use.
     */
    public JsonAdaptedNotes(Notes source) {
        student = source.getStudent();
        content = source.getContent();
        priority = source.getPriority();
        dateTime = source.getDateTime();
    }

    /**
     * Converts this Jackson-friendly adapted notes object into the model's {@code Notes} object.
     * @throws IllegalValueException
     */
    public Notes toModelType() throws IllegalValueException {
        if (student == null || content == null || priority == null || dateTime == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        return new Notes(student, content, priority, dateTime);
    }



}

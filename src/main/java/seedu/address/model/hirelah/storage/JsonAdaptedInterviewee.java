package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.Interviewee;

/**
 * Jackson-friendly version of {@link Interviewee}.
 */
class JsonAdaptedInterviewee {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private String fullName;
    private final int id;
    private String alias;
    // private ObjectProperty<File> resume;


    /**
     * Constructs a {@code JsonAdaptedInterviewee} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedInterviewee(@JsonProperty("fullName") String fullName,
                                  @JsonProperty("id") int id, @JsonProperty("alias") String alias) {
        this.fullName = fullName;
        this.id = id;
        this.alias = alias;
        // this.resume = resume;
        //this.interview = interview;
    }
    public JsonAdaptedInterviewee(Interviewee source) {
        fullName = source.getFullName();
        id = source.getId();
        alias = source.getAlias().orElseGet(() -> null);
        // resume = source.resumeProperty();
        // interview = source.getTranscript();
    }
    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Interviewee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */

    public Interviewee toModelType() throws IllegalValueException {
        if (fullName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        if (Integer.valueOf(id) == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ID"));
        }
        return new Interviewee(fullName, id); // do not contain the recording, alias and interview session
    }
}

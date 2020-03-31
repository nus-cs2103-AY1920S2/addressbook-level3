package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.ObjectProperty;
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
    private final boolean transcript;


    /**
     * Constructs a {@code JsonAdaptedInterviewee} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedInterviewee(@JsonProperty("fullName") String fullName,
                                  @JsonProperty("id") int id, @JsonProperty("alias") String alias,
                                  @JsonProperty("transcript") boolean transcript) {
        this.fullName = fullName;
        this.id = id;
        this.alias = alias;
        this.transcript = transcript;
    }
    public JsonAdaptedInterviewee(Interviewee source) {
        fullName = source.getFullName();
        id = source.getId();
        alias = source.getAlias().orElseGet(() -> null);
        if (source.getTranscript().isEmpty()){
            transcript = false;
        }
        else {
            transcript = true;
        }
    }
    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Interviewee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */

    public Interviewee toModelType(TranscriptStorage transcriptStorage) throws IllegalValueException {

        return new Interviewee(fullName, id); //
    }
}

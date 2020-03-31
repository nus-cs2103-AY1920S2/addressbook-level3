package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.ObjectProperty;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.QuestionList;
import seedu.address.model.hirelah.Transcript;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Jackson-friendly version of {@link Interviewee}.
 */
class JsonAdaptedInterviewee {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private String fullName;
    private final Integer id;
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

    public Interviewee toModelType(QuestionList questionList, AttributeList attributeList, TranscriptStorage storage, Boolean finalised) throws IllegalValueException, DataConversionException {
        Interviewee interviewee =  new Interviewee(fullName, id);
        if (transcript) {
            String location = storage.toString() + id.toString() + ".json";
            Path path = Paths.get(location);
            Optional<Transcript> transcript = storage.readTranscript(path,questionList,attributeList);
            if(transcript.isEmpty() || finalised) {
                throw new IllegalValueException("There is is an error in loading the transcript for " + fullName); }
            interviewee.setTranscript(transcript.get());
        }
        if (alias != null) {
            interviewee.setAlias(alias);
        }
        return interviewee;
    }
}

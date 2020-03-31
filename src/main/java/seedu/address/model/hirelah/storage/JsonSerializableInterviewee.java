package seedu.address.model.hirelah.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.IntervieweeList;
import seedu.address.model.hirelah.QuestionList;

/**
 * An Immutable InterviewList that is serializable to JSON format {@link IntervieweeList}.
 */
@JsonRootName(value = "interviewees")
class JsonSerializableInterviewee {
    public static final String MESSAGE_DUPLICATE_INTERVIEWEE = "Interviewee list contains duplicate";
    private final List<JsonAdaptedInterviewee> interviewees = new ArrayList<>();

    /**
     * used to deserialise the interviewee
     * object from json file to java object.
     * @param interviewees list of object
     */
    @JsonCreator
    public JsonSerializableInterviewee(@JsonProperty("interviewees") List<JsonAdaptedInterviewee> interviewees) {
        this.interviewees.addAll(interviewees);
    }

    public JsonSerializableInterviewee(IntervieweeList source) {
        List<Interviewee> convertedList = source.getObservableList();
        interviewees.addAll(convertedList.stream().map(JsonAdaptedInterviewee::new).collect(Collectors.toList()));
    }

    /**
     * Converts into an IntervieweeList
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public IntervieweeList toModelType(QuestionList questionList, AttributeList attributeList, TranscriptStorage storage, Boolean finalised ) throws IllegalValueException, DataConversionException {
        IntervieweeList newData = new IntervieweeList();

        for (JsonAdaptedInterviewee jsonAdaptedInterviewee : interviewees) {
            Interviewee interviewee = jsonAdaptedInterviewee.toModelType(questionList, attributeList, storage, finalised);
            newData.addInterviewee(interviewee); //to be confirm
        }
        return newData;
    }
}

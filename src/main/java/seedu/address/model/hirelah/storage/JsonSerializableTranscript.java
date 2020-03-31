package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.Question;
import seedu.address.model.hirelah.QuestionList;
import seedu.address.model.hirelah.Transcript;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JsonSerializableTranscript {
    private final HashMap<String,Double> attributeToScore;
    private final List<JsonAdaptedRemark> remarkList;
    private final Instant startTime;
    private final boolean completed;

    @JsonCreator
    public JsonSerializableTranscript(@JsonProperty("attributeToScore") HashMap<String,Double> attributeToScore ,
                                      @JsonProperty("remarkList") List<JsonAdaptedRemark> remarkList,
                                      @JsonProperty("completed") boolean completed,
                                      @JsonProperty("startTime") Instant startTime) {
        this.attributeToScore = attributeToScore;
        this.remarkList = remarkList;
        this.startTime = startTime;
        this.completed = completed;
    }

    public JsonSerializableTranscript(Transcript transcript) {
        this.attributeToScore = new HashMap<>();
        transcript.getAttributeToScoreMapView()
                .forEach((attribute, score) -> this.attributeToScore.put(attribute.toString(), score));
        this.remarkList = transcript.getRemarkListView().stream()
                .map(JsonAdaptedRemark::new).collect(Collectors.toList());
        this.startTime = transcript.getStartTime();
        this.completed = transcript.isCompleted();
    }

    public Transcript toModelType(QuestionList questions, AttributeList attributes)
            throws IllegalValueException, IllegalActionException {
        Transcript transcript = new Transcript(questions, attributes, startTime);
        for (JsonAdaptedRemark remark : remarkList) {
            if (remark.getQuestionNumber() == null) {
                transcript.addRemark(remark.getMessage(), remark.getTime());
            } else {
                Question questionToCheck = questions.find(remark.getQuestionNumber());
                if (questionToCheck.toString().equals(remark.getMessage())) {
                    transcript.startQuestion(remark.getQuestionNumber(), questionToCheck, remark.getTime());
                } else {
                    throw new IllegalValueException("Question in Transcript does not match question list!");
                }
            }
        }
        return transcript;
    }
}

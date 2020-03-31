package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.hirelah.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonSerializableTranscript {
    private final HashMap<String,Double> attributeToScore;
    private final List<JsonAdaptedRemark> remarkList;
    private final int numbOfQn;
    private final Instant startTime;
    private final boolean completed;

    @JsonCreator
    public JsonSerializableTranscript(@JsonProperty("attributeToScore") HashMap<String,Double> attributeToScore ,
                                      @JsonProperty("remarkList") List<JsonAdaptedRemark> remarkList,
                                      @JsonProperty("numbOfQn") int numbOfQn, @JsonProperty("completed") boolean completed,
                                      @JsonProperty("startTime") Instant startTime) {
        this.attributeToScore = attributeToScore;
        this.remarkList = remarkList;
        this.numbOfQn = numbOfQn;
        this.startTime = startTime;
        this.completed = completed;
    }

    public JsonSerializableTranscript(Transcript transcript) {
        this.attributeToScore = new HashMap<>();
        this.remarkList = new ArrayList<>();
        List<Remark> source = transcript.getRemarkListView();
        numbOfQn = transcript.getNumbOfQns();
        startTime = transcript.getStartTime();
        completed = transcript.isCompleted();
        this.remarkList.addAll(source.stream().map(JsonAdaptedRemark::new).collect(Collectors.toList()));
        HashMap<Attribute,Double> toBecopied = transcript.getMap();
        for (Map.Entry<Attribute, Double> entry : toBecopied.entrySet()) {
            String key = entry.getKey().toString();
            Double value = entry.getValue();
            attributeToScore.put(key, value);
        }
    }


    public Transcript toModelType(QuestionList questionList, AttributeList attributeList) {
        RemarkList newList = new RemarkList(numbOfQn, startTime);
        for (JsonAdaptedRemark adaptedRemark = remarkList) {
            Remark remark = adaptedRemark.toModelType();
            newList.getRemarks().add(remark);
        }
        //return new Transcript(attributeToScore, newList);
    }

}

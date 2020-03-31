package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.hirelah.Question;
import seedu.address.model.hirelah.QuestionRemark;
import seedu.address.model.hirelah.Remark;

import java.time.Duration;

public class JsonAdaptedRemark {
    private final Duration time;
    private final String message;
    private final Integer number;

    @JsonCreator
    public JsonAdaptedRemark(@JsonProperty("newTime") Duration newTime, @JsonProperty("newMessage") String newMessage,
                             @JsonProperty("number") Integer newNumber) {
        time = newTime;
        message = newMessage;
        number = newNumber;
    }

    public JsonAdaptedRemark(Remark source) {
        time = source.getTime();
        message = source.getMessage();
        if (source instanceof QuestionRemark) {
            number = ((QuestionRemark) source).getQuestionNumber();
        }
        else {
            number = null;
        }
    }
    public Remark toModelType() {
        if (number == null ){
            return new Remark(time,message);
        }
        return new QuestionRemark(time, number,new Question(message));
    }
}

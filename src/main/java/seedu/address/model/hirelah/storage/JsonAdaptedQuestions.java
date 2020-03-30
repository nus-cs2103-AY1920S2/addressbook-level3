package seedu.address.model.hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.Question;

/**
 * Jackson-friendly version of {@link Question}.
 */
public class JsonAdaptedQuestions {
    private final String description;

    @JsonCreator
    public JsonAdaptedQuestions(@JsonProperty("description") String description) {
        this.description = description;
    }

    public JsonAdaptedQuestions(Question source) {
        description = source.toString();
    }
    /**
     * Converts this Jackson-friendly adapted question object into the model's {@code Question} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted question.
     */
    public Question toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException("Invalid Question");
        }
        return new Question(description);
    }
}

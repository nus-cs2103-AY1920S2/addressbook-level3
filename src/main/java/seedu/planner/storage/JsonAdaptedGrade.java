package seedu.planner.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.grades.Grade;
import seedu.planner.model.grades.LetterGrade;

/**
 * Jackson-friendly version of {@link Grade}.
 * Stores the {@code letterGrade} using its {@code inputName} instead of its {@code toString()} value.
 */
public class JsonAdaptedGrade {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Grade's %s field is missing!";

    protected final String letterGrade;
    protected final boolean isSu;

    @JsonCreator
    public JsonAdaptedGrade(@JsonProperty("letterGrade") String letterGrade, @JsonProperty("isSu") boolean isSu) {
        this.letterGrade = letterGrade;
        this.isSu = isSu;
    }

    /**
     * Converts the given {@code Grade} into this class for Jackson use.
     */
    public JsonAdaptedGrade(Grade source) {
        this.letterGrade = source.letterGrade.toString();
        this.isSu = source.isSu;
    }

    /**
     * Converts this Jackson-friendly adapted grade into the model's {@code Grade} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted grade.
     */
    public Grade toModelType() throws IllegalValueException {

        if (letterGrade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LetterGrade.class.getSimpleName()));
        }

        try {
            return new Grade(LetterGrade.fromInputName(letterGrade), isSu);
        } catch (IllegalArgumentException ex) {
            throw new IllegalValueException("Invalid Grade: " + letterGrade);
        }
    }
}

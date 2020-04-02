package seedu.recipe.storage.plan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.Date;

/**
 * Jackson-friendly version of {@link Date}.
 */
public class JsonAdaptedPlannedDate {

    private final String date;

    /**
     * Constructs a {@code JsonAdaptedPlannedDate} with the given {@code plannedDate}.
     */
    @JsonCreator
    public JsonAdaptedPlannedDate(String plannedDate) {
        date = plannedDate;
    }

    /**
     * Converts a given {@code plannedDate} into this class for Jackson use.
     */
    public JsonAdaptedPlannedDate(Date plannedDate) {
        date = plannedDate.toStringForJson();
    }

    @JsonValue
    public String getDate() {
        return date;
    }

    /**
     * Converts this Jackson-friendly adapted plannedDate object into the model's {@code Date} object.
     * @return Date object that the adapted plannedDate was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted Date.
     */
    public Date toModelType() throws IllegalValueException {
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        return new Date(date);
    }
}

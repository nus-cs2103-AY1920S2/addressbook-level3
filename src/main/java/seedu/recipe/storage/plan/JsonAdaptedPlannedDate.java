package seedu.recipe.storage.plan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.plan.PlannedDate;

/**
 * Jackson-friendly version of {@link PlannedDate}.
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
    public JsonAdaptedPlannedDate(PlannedDate plannedDate) {
        date = plannedDate.toStringForJson();
    }

    @JsonValue
    public String getDate() {
        return date;
    }

    /**
     * Converts this Jackson-friendly adapted plannedDate object into the model's {@code PlannedDate} object.
     * @return PlannedDate object that the adapted plannedDate was converted into.
     * @throws IllegalValueException if there were any data constraints violated in the adapted PlannedDate.
     */
    public PlannedDate toModelType() throws IllegalValueException {
        if (!PlannedDate.isValidDate(date)) {
            throw new IllegalValueException(PlannedDate.MESSAGE_CONSTRAINTS);
        }

        return new PlannedDate(date);
    }
}

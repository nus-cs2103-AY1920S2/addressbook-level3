package seedu.zerotoone.storage.schedule.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.schedule.DateTime;

/**
 * Jackson-friendly version of {@link DateTime}.
 */
public class JacksonDateTime {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "DateTime's %s field is missing!";

    private final String dateTime;

    @JsonCreator
    public JacksonDateTime(@JsonProperty("dateTime") String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Converts a given {@code DateTime} into this class for Jackson use.
     */
    public JacksonDateTime(DateTime source) {
        dateTime = source.toString();
    }

    /**
     * Converts this Jackson-friendly adapted DateTime object into the model's {@code DateTime} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted DateTime.
     */
    public DateTime toModelType() throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        } else if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }

        return new DateTime(dateTime);
    }
}

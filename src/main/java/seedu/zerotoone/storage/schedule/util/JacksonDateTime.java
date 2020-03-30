package seedu.zerotoone.storage.schedule.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.schedule.DateTime;

/**
 *
 */
public class JacksonDateTime {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "DateTime's %s field is missing!";

    private final String dateTime;

    @JsonCreator
    public JacksonDateTime(@JsonProperty("dateTime") String dateTime) {
        this.dateTime = dateTime;
    }

    public JacksonDateTime(DateTime source) {
        dateTime = source.toString(); // MAY_NOT_WORK
    }

    /**
     *
     * @return
     * @throws IllegalValueException
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

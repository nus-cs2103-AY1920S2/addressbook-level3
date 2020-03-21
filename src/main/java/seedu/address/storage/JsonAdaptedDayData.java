package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DayData;

/** Jackson-friendly version of {@link DayData}. */
class JsonAdaptedDayData {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "DayData's %s field is missing!";

    private final String date;
    private final String pomDurationData;
    private final String tasksDoneData;

    /** Constructs a {@code JsonAdaptedDayData} with the given person details. */
    @JsonCreator
    public JsonAdaptedDayData(
            @JsonProperty("date") String date,
            @JsonProperty("pomDurationData") String pomDurationData,
            @JsonProperty("tasksDoneData") String tasksDoneData) {
        this.date = date;
        this.pomDurationData = pomDurationData;
        this.tasksDoneData = tasksDoneData;
    }

    /** Converts a given {@code Task} into this class for Jackson use. */
    public JsonAdaptedDayData(DayData source) {
        date = source.getDate();
        pomDurationData = source.getPomDurationData();
        tasksDoneData = source.getTasksDoneData();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code DayData} object.
     * TODO better validation
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *     person.
     */
    public DayData toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "date")); //Name.class.getSimpleName()));
        }

        if (pomDurationData == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "pomDurationData")); //Name.class.getSimpleName()));
        }

        if (tasksDoneData == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "tasksDoneData")); //Name.class.getSimpleName()));
        }

        return new DayData(date, pomDurationData, tasksDoneData);
    }
}

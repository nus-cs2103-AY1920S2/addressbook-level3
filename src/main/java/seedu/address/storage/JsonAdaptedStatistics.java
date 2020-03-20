package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Statistics;
import seedu.address.model.ReadOnlyStatistics;

import java.util.ArrayList;
import java.util.List;

@JsonRootName(value = "statistics")
class JsonAdaptedStatistics {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Statisitcs' %s field is missing!";

    public int medals;
    public List<String> pomDurationData = new ArrayList<>();
    public List<String> tasksDoneData = new ArrayList<>();

    /** Constructs a {@code JsonAdaptedStatistics} with the statistics details. */
    @JsonCreator
    public JsonAdaptedStatistics(
            @JsonProperty("medals") int medals,
            @JsonProperty("pomDurationData") List<String> pomDurationData,
            @JsonProperty("tasksDoneData") List<String> tasksDoneData) {
        this.medals = medals;
        if (pomDurationData != null) {
            this.pomDurationData = pomDurationData;
        }
        if (tasksDoneData != null) {
            this.tasksDoneData = tasksDoneData;
        }
    }

    /** Converts a given {@code Statistics} into this class for use. */
    public JsonAdaptedStatistics(ReadOnlyStatistics source) {
        this.medals = source.getMedals();
        this.pomDurationData = source.getPomDurationData();
        this.tasksDoneData = source.getTasksDoneData();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in statistics.
     */
    public Statistics toModelType() throws IllegalValueException {
        return new Statistics(medals, pomDurationData, tasksDoneData);
    }
}
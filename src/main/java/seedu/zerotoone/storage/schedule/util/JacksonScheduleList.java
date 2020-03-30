package seedu.zerotoone.storage.schedule.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.schedule.ScheduleList;

/**
 * An Immutable ScheduleList that is serializable to JSON format.
 */
@JsonRootName(value = "schedulelist")
public class JacksonScheduleList {

    public static final String MESSAGE_DUPLICATE_SCHEDULE = "Schedules list contains duplicate schedule(s).";

    private final List<JacksonSchedule> schedules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableScheduleList} with the given schedules.
     */
    @JsonCreator
    public JacksonScheduleList(@JsonProperty("schedules") List<JacksonSchedule> schedules) {
        this.schedules.addAll(schedules);
    }

    /**
     * Converts a given {@code ScheduleList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableScheduleList}.
     */
    public JacksonScheduleList(ScheduleList source) {
        schedules.addAll(source.getScheduleList().stream().map(JacksonSchedule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this schedule list into the model's {@code ScheduleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ScheduleList toModelType() throws IllegalValueException {
        ScheduleList scheduleList = new ScheduleList();
        for (JacksonSchedule jacksonSchedule : schedules) {
            Schedule schedule = jacksonSchedule.toModelType();
            if (scheduleList.hasSchedule(schedule)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SCHEDULE);
            }
            scheduleList.addSchedule(schedule);
        }
        return scheduleList;
    }
}

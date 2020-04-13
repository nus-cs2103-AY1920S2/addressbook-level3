package seedu.zerotoone.storage.schedule.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.schedule.DateTime;
import seedu.zerotoone.model.schedule.OneTimeSchedule;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Jackson-friendly version of {@link OneTimeSchedule}.
 */
public class JacksonSchedule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Schedule's %s field is missing!";

    private final String workoutNameToSchedule;
    private final JacksonDateTime dateTime;

    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given schedule details.
     */
    @JsonCreator
    public JacksonSchedule(@JsonProperty("workoutNameToSchedule") String workoutNameToSchedule,
                           @JsonProperty("dateTime") JacksonDateTime dateTime) {
        this.workoutNameToSchedule = workoutNameToSchedule;
        this.dateTime = dateTime;
    }

    /**
     * Converts a given {@code Schedule} into this class for Jackson use.
     */
    public JacksonSchedule(Schedule source) {
        OneTimeSchedule oneTimeSchedule = (OneTimeSchedule) source;
        workoutNameToSchedule = oneTimeSchedule.getWorkoutNameToSchedule().fullName;
        dateTime = new JacksonDateTime(oneTimeSchedule.getDateTime());
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted schedule.
     */
    public Schedule toModelType() throws IllegalValueException {
        if (workoutNameToSchedule == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            WorkoutName.class.getSimpleName()));
        } else if (!WorkoutName.isValidWorkoutName(workoutNameToSchedule)) {
            throw new IllegalValueException(WorkoutName.MESSAGE_CONSTRAINTS);
        }

        final WorkoutName workoutName = new WorkoutName(workoutNameToSchedule);
        final DateTime dateTime = this.dateTime.toModelType();

        return new OneTimeSchedule(workoutName, dateTime);
    }

}

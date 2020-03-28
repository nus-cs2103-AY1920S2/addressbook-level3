package seedu.zerotoone.storage.schedule.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.schedule.DateTime;
import seedu.zerotoone.model.schedule.OneTimeSchedule;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.storage.exercise.util.JacksonExercise;

/**
 *
 */
public class JacksonSchedule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Schedule's %s field is missing!";

    private final JacksonExercise workoutToSchedule; // TO_CHANGE_EXERCISE_TO_WORKOUT
    private final JacksonDateTime dateTime;

    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given schedule details.
     */
    @JsonCreator
    public JacksonSchedule(@JsonProperty("workoutToSchedule") JacksonExercise workoutToSchedule,
                           @JsonProperty("dateTime") JacksonDateTime dateTime) {
        this.workoutToSchedule = workoutToSchedule;
        this.dateTime = dateTime;
    }

    /**
     * Converts a given {@code Schedule} into this class for Jackson use.
     */
    public JacksonSchedule(Schedule source) {
        OneTimeSchedule oneTimeSchedule = (OneTimeSchedule) source;
        workoutToSchedule = new JacksonExercise(oneTimeSchedule.getWorkoutToSchedule());
        dateTime = new JacksonDateTime(oneTimeSchedule.getDateTime());
        // STEPH_TODO: add support for recurring schedule
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted schedule.
     */
    public Schedule toModelType() throws IllegalValueException {
        Exercise exercise = workoutToSchedule.toModelType();
        DateTime dateTime = this.dateTime.toModelType();

        return new OneTimeSchedule(exercise, dateTime);
    }

}

package seedu.zerotoone.storage.log.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.storage.log.util.JacksonCompletedWorkout.INVALID_TIME_FORMAT_MESSAGE;
import static seedu.zerotoone.storage.workout.util.JacksonWorkout.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.log.TypicalLogs.ARMS_WORKOUT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.workout.WorkoutName;

class JacksonCompletedWorkoutTest {
    private static final String VALID_WORKOUT_NAME = "valid workout name";
    private static final String INVALID_WORKOUT_NAME = "jbcjb@#dv+123+";
    private static final String VALID_DATE_TIME = "2020-02-08 18:00";
    private static final String INVALID_DATE_TIME = "2020-02-08 invalid date";

    private static final List<JacksonCompletedExercise> VALID_WORKOUT_EXERCISES = ARMS_WORKOUT.getExercises().stream()
        .map(JacksonCompletedExercise::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validCompletedWorkoutDetails_returnsCompletedWorkout() throws Exception {
        JacksonCompletedWorkout workout = new JacksonCompletedWorkout(ARMS_WORKOUT);
        assertEquals(ARMS_WORKOUT, workout.toModelType());
    }

    @Test
    public void toModelType_invalidCompletedWorkoutName_throwsIllegalValueException() {
        JacksonCompletedWorkout workout = new JacksonCompletedWorkout(INVALID_WORKOUT_NAME,
            VALID_WORKOUT_EXERCISES,
            VALID_DATE_TIME,
            VALID_DATE_TIME);
        String expectedMessage = WorkoutName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullWorkoutName_throwsIllegalValueException() {
        JacksonCompletedWorkout workout = new JacksonCompletedWorkout(null,
            VALID_WORKOUT_EXERCISES,
            VALID_DATE_TIME,
            VALID_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WorkoutName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JacksonCompletedWorkout workout = new JacksonCompletedWorkout(
            VALID_WORKOUT_NAME,
            VALID_WORKOUT_EXERCISES,
            null,
            VALID_DATE_TIME);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "StartTime");
        assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JacksonCompletedWorkout workout = new JacksonCompletedWorkout(VALID_WORKOUT_NAME,
            VALID_WORKOUT_EXERCISES,
            VALID_DATE_TIME,
            null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndTime");
        assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JacksonCompletedWorkout workout = new JacksonCompletedWorkout(
            VALID_WORKOUT_NAME,
            VALID_WORKOUT_EXERCISES,
            INVALID_DATE_TIME,
            VALID_DATE_TIME);

        assertThrows(IllegalValueException.class, INVALID_TIME_FORMAT_MESSAGE, workout::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JacksonCompletedWorkout workout = new JacksonCompletedWorkout(VALID_WORKOUT_NAME,
            VALID_WORKOUT_EXERCISES,
            VALID_DATE_TIME,
            INVALID_DATE_TIME);

        assertThrows(IllegalValueException.class, INVALID_TIME_FORMAT_MESSAGE, workout::toModelType);
    }
}

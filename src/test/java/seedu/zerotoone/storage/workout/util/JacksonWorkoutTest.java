package seedu.zerotoone.storage.workout.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.storage.workout.util.JacksonWorkout.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.ARMS_WORKOUT;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.storage.exercise.util.JacksonExercise;

public class JacksonWorkoutTest {
    private static final String INVALID_WORKOUT_NAME = "4rm5w0rk0u+";

    private static final List<JacksonExercise> VALID_WORKOUT_EXERCISES = ARMS_WORKOUT.getWorkoutExercises().stream()
            .map(JacksonExercise::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validWorkoutDetails_returnsWorkout() throws Exception {
        JacksonWorkout workout = new JacksonWorkout(ARMS_WORKOUT);
        assertEquals(ARMS_WORKOUT, workout.toModelType());
    }

    @Test
    public void toModelType_invalidWorkoutName_throwsIllegalValueException() {
        JacksonWorkout workout = new JacksonWorkout(INVALID_WORKOUT_NAME, VALID_WORKOUT_EXERCISES);
        String expectedMessage = WorkoutName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullWorkoutName_throwsIllegalValueException() {
        JacksonWorkout workout = new JacksonWorkout(null, VALID_WORKOUT_EXERCISES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WorkoutName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }
}

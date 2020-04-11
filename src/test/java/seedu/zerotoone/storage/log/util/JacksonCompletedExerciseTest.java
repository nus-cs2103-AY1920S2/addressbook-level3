package seedu.zerotoone.storage.log.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.storage.exercise.util.JacksonExercise.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.zerotoone.storage.log.util.JacksonCompletedExercise.INVALID_TIME_FORMAT_MESSAGE;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.log.TypicalCompletedExercises.BENCH_PRESS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.session.CompletedSet;

class JacksonCompletedExerciseTest {
    private static final String VALID_COMPLETED_EXERCISE_NAME = "Jiachen";
    private static final String INVALID_COMPLETED_EXERCISE_NAME = "Ji@chen";
    private static final String VALID_DATE_TIME = "2020-02-08 18:00";
    private static final String INVALID_DATE_TIME = "2020-02-08 invalid date";

    private static final List<JacksonCompletedSet> VALID_EXERCISE_SETS = BENCH_PRESS.getSets().stream()
        .map(JacksonCompletedSet::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validCompletedExercise_returnsCompletedExercise() throws Exception {
        JacksonCompletedExercise completeExercise = new JacksonCompletedExercise(BENCH_PRESS);
        assertEquals(BENCH_PRESS, completeExercise.toModelType());
    }

    @Test
    public void toModelType_validCompletedExerciseDetails_returnsCompletedExercise() throws Exception {
        List<JacksonCompletedSet> exerciseSets = new ArrayList<>();
        for (CompletedSet exerciseSet : BENCH_PRESS.getSets()) {
            exerciseSets.add(new JacksonCompletedSet(exerciseSet));
        }
        JacksonCompletedExercise exercise = new JacksonCompletedExercise(
            BENCH_PRESS.getExerciseName().fullName, exerciseSets,
            BENCH_PRESS.getStartTime().format(JacksonCompletedExercise.getDateTimeFormatter()),
            BENCH_PRESS.getEndTime().format(JacksonCompletedExercise.getDateTimeFormatter())
        );
        assertEquals(BENCH_PRESS, exercise.toModelType());
    }

    @Test
    public void toModelType_invalidExerciseName_throwsIllegalValueException() {
        JacksonCompletedExercise exercise = new JacksonCompletedExercise(INVALID_COMPLETED_EXERCISE_NAME,
            VALID_EXERCISE_SETS, VALID_DATE_TIME, VALID_DATE_TIME);
        String expectedMessage = ExerciseName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_invalidStartRange_throwsIllegalValueException() {
        JacksonCompletedExercise exercise = new JacksonCompletedExercise(VALID_COMPLETED_EXERCISE_NAME,
            VALID_EXERCISE_SETS, INVALID_DATE_TIME, VALID_DATE_TIME);
        assertThrows(IllegalValueException.class, INVALID_TIME_FORMAT_MESSAGE, exercise::toModelType);
    }

    @Test
    public void toModelType_invalidEndRange_throwsIllegalValueException() {
        JacksonCompletedExercise exercise = new JacksonCompletedExercise(VALID_COMPLETED_EXERCISE_NAME,
            VALID_EXERCISE_SETS, VALID_DATE_TIME, INVALID_DATE_TIME);
        assertThrows(IllegalValueException.class, INVALID_TIME_FORMAT_MESSAGE, exercise::toModelType);
    }

    @Test
    public void toModelType_nullExerciseName_throwsIllegalValueException() {
        JacksonCompletedExercise exercise = new JacksonCompletedExercise(null,
            VALID_EXERCISE_SETS,
            VALID_DATE_TIME,
            VALID_DATE_TIME);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ExerciseName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }
}

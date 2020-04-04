package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExerciseWeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExerciseWeight(null));
    }

    @Test
    public void constructor_invalidExerciseReps_throwsIllegalArgumentException() {
        String invalidExerciseWeight = "i";
        assertThrows(IllegalArgumentException.class, () -> new ExerciseWeight(invalidExerciseWeight));
    }

    @Test
    public void isValidExerciseWeight() {
        // null weight
        assertThrows(NullPointerException.class, () -> ExerciseWeight.isValidExerciseWeight(null));

        // invalid weight
        assertFalse(ExerciseWeight.isValidExerciseWeight(" ")); // spaces only
        assertFalse(ExerciseWeight.isValidExerciseWeight("ab")); // contains no numbers
        assertFalse(ExerciseWeight.isValidExerciseWeight(".2")); // decimal numbers
        assertFalse(ExerciseWeight.isValidExerciseWeight("-23")); // negative numbers
        assertFalse(ExerciseWeight.isValidExerciseWeight("-23.23")); // negative decimals
        assertFalse(ExerciseWeight.isValidExerciseWeight("as.23")); // inclusion of letters
        assertFalse(ExerciseWeight.isValidExerciseWeight("23.as")); // inclusion of letters
        assertFalse(ExerciseWeight.isValidExerciseWeight("23.a1")); // inclusion of letters
        assertFalse(ExerciseWeight.isValidExerciseWeight("23z1")); // inclusion of letters
        assertFalse(ExerciseWeight.isValidExerciseWeight("23/.23")); // inclusion of symbols
        assertFalse(ExerciseWeight.isValidExerciseWeight("2-3.23")); // inclusion of symbols
        assertFalse(ExerciseWeight.isValidExerciseWeight("23.2'3")); // inclusion of symbols

        // valid weight
        assertTrue(ExerciseWeight.isValidExerciseWeight("")); // empty string
        assertTrue(ExerciseWeight.isValidExerciseWeight("0")); // single digit
        assertTrue(ExerciseWeight.isValidExerciseWeight("5")); // single digit
        assertTrue(ExerciseWeight.isValidExerciseWeight("003")); // leading zeros
        assertTrue(ExerciseWeight.isValidExerciseWeight("456")); // numbers only
    }

}

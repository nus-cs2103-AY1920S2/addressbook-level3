package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ExerciseDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExerciseDate(null));
    }

    @Test
    public void isValidExerciseDate() {
        // null birthday
        assertThrows(NullPointerException.class, () -> ExerciseDate.isValidExerciseDate(null));

        // invalid birthdays
        assertFalse(ExerciseDate.isValidExerciseDate("")); // empty string
        assertFalse(ExerciseDate.isValidExerciseDate(" ")); // spaces only
        assertFalse(ExerciseDate.isValidExerciseDate("91")); // random numbers
        assertFalse(ExerciseDate.isValidExerciseDate("not a birthday")); // non-numeric
        assertFalse(ExerciseDate.isValidExerciseDate("not a birthday1")); // non-numeric
        assertFalse(ExerciseDate.isValidExerciseDate("not a birthday2")); // non-numeric
        assertFalse(ExerciseDate.isValidExerciseDate("not a birthday3")); // non-numeric
        assertFalse(ExerciseDate.isValidExerciseDate("not a birthday4")); // non-numeric
        assertFalse(ExerciseDate.isValidExerciseDate("11-e33-1998")); // erroneous alphanumeric
        assertFalse(ExerciseDate.isValidExerciseDate("11/12/1213")); // incorrect delimiter
        assertFalse(ExerciseDate.isValidExerciseDate("11-13-1213")); // months more than 12
        assertFalse(ExerciseDate.isValidExerciseDate("32-11-1213")); // dates more than 31
        assertFalse(ExerciseDate.isValidExerciseDate("9-11-12131")); // years more than 4 digits

        // valid birthday
        assertTrue(ExerciseDate.isValidExerciseDate("01-01-1980")); // exercise date on 01-01-1980
        assertTrue(ExerciseDate.isValidExerciseDate("01-01-2820")); // exercise date on 01-01-2820
        assertTrue(ExerciseDate.isValidExerciseDate("31-01-2010")); // exercise date on 31-01-2010

        assertTrue(new ExerciseDate("01-01-1980").hashCode() == new ExerciseDate("01-01-1980").hashCode());
        assertTrue(new ExerciseDate("01-01-1980").toString().equals("01-01-1980") == true);
    }
}

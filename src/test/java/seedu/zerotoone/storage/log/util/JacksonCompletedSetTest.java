package seedu.zerotoone.storage.log.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.zerotoone.storage.log.util.JacksonCompletedSet.MALFORMED_BOOLEAN_MESSAGE;
import static seedu.zerotoone.storage.log.util.JacksonCompletedSet.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.CompletedSet;

class JacksonCompletedSetTest {
    @Test
    public void toModelType_nullWeight_throwIllegalValueException() {
        Exception exceptionThrown = assertThrows(IllegalValueException.class, () ->
            new JacksonCompletedSet(null, "20", "true").toModelType());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
            Weight.class.getSimpleName());
        assertEquals(exceptionThrown.getMessage(), expectedMessage);
    }

    @Test
    public void toModelType_invalidWeight_throwIllegalValueException() {
        Exception exceptionThrown = assertThrows(IllegalValueException.class, () ->
            new JacksonCompletedSet("01", "20", "true").toModelType());
        String expectedMessage = Weight.MESSAGE_CONSTRAINTS;
        assertEquals(exceptionThrown.getMessage(), expectedMessage);
    }

    @Test
    public void toModelType_nullNumReps_throwIllegalValueException() {
        Exception exceptionThrown = assertThrows(IllegalValueException.class, () ->
            new JacksonCompletedSet("10", null, "true").toModelType());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
            NumReps.class.getSimpleName());
        assertEquals(exceptionThrown.getMessage(), expectedMessage);
    }

    @Test
    public void toModelType_invalidNumReps_throwIllegalValueException() {
        Exception exceptionThrown = assertThrows(IllegalValueException.class, () ->
            new JacksonCompletedSet("10", "01", "true").toModelType());
        String expectedMessage = NumReps.MESSAGE_CONSTRAINTS;
        assertEquals(exceptionThrown.getMessage(), expectedMessage);
    }

    @Test
    public void toModelType_nullIsFinished_throwIllegalValueException() {
        Exception exceptionThrown = assertThrows(IllegalValueException.class, () ->
            new JacksonCompletedSet("10", "10", null).toModelType());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "isFinished");
        assertEquals(exceptionThrown.getMessage(), expectedMessage);
    }

    @Test
    public void toModelType_invalidIsFinished_throwIllegalValueException() {
        Exception exceptionThrown = assertThrows(IllegalValueException.class, () ->
            new JacksonCompletedSet("10", "10", "invalid").toModelType());
        assertEquals(MALFORMED_BOOLEAN_MESSAGE, exceptionThrown.getMessage());
    }

    @Test
    public void toModelType_validWorkoutSetDetails_returnsCompletedSet() throws IllegalValueException {
        CompletedSet expectedCompletedSet = new CompletedSet(
            new Weight("10"),
            new NumReps("20"),
            true);

        CompletedSet actual = new JacksonCompletedSet("10", "20", "true").toModelType();

        assertEquals(expectedCompletedSet, actual);
    }

    @Test
    public void toModelType_validWorkoutSet_returnsCompletedSet() throws IllegalValueException {
        CompletedSet expectedCompletedSet = new CompletedSet(
            new Weight("10"),
            new NumReps("20"),
            true);

        assertEquals(expectedCompletedSet,
            new JacksonCompletedSet(expectedCompletedSet).toModelType());
    }

}

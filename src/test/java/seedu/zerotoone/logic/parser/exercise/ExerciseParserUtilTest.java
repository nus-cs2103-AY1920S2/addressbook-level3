package seedu.zerotoone.logic.parser.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.exercise.ExerciseName;

public class ExerciseParserUtilTest {
    @Test
    public void parseExerciseName_nullExerciseName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                ExerciseParserUtil.parseExerciseName(null));
    }

    @Test
    public void parseExerciseName_invalidExerciseName_throwsParseException() {
        Exception exceptionThrown;

        // Empty String
        exceptionThrown = assertThrows(ParseException.class, () ->
                ExerciseParserUtil.parseExerciseName(""));
        assertEquals(ExerciseName.MESSAGE_CONSTRAINTS, exceptionThrown.getMessage());

        // Start with non-alphabets
        exceptionThrown = assertThrows(ParseException.class, () ->
                ExerciseParserUtil.parseExerciseName("1Situp"));
        assertEquals(ExerciseName.MESSAGE_CONSTRAINTS, exceptionThrown.getMessage());

        // Has Symbols
        exceptionThrown = assertThrows(ParseException.class, () ->
                ExerciseParserUtil.parseExerciseName("Bench&Press"));
        assertEquals(ExerciseName.MESSAGE_CONSTRAINTS, exceptionThrown.getMessage());

        // All Spaces
        exceptionThrown = assertThrows(ParseException.class, () ->
                ExerciseParserUtil.parseExerciseName("        "));
        assertEquals(ExerciseName.MESSAGE_CONSTRAINTS, exceptionThrown.getMessage());

        // Spaces then invalid
        exceptionThrown = assertThrows(ParseException.class, () ->
                ExerciseParserUtil.parseExerciseName("    %invalid%    "));
        assertEquals(ExerciseName.MESSAGE_CONSTRAINTS, exceptionThrown.getMessage());
    }

    @Test
    public void parseExerciseName_validExerciseName_returnExerciseName() throws ParseException {
        ExerciseName expectedExerciseName = new ExerciseName("Bench Press");

        assertEquals(ExerciseParserUtil.parseExerciseName("Bench Press"), expectedExerciseName);
        assertEquals(ExerciseParserUtil.parseExerciseName("   Bench Press   "), expectedExerciseName);
        assertNotEquals(ExerciseParserUtil.parseExerciseName("bench press"), expectedExerciseName);
        assertNotEquals(ExerciseParserUtil.parseExerciseName("BenchPress"), expectedExerciseName);
    }
}

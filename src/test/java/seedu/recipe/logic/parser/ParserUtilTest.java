package seedu.recipe.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.Time;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TIME = "+651234";
    private static final String INVALID_STEP = "   ";
    private static final String INVALID_GOAL = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_TIME = "123456";
    private static final String VALID_STEP = "rachel@example.com";
    private static final String VALID_GOAL_1 = "friend";
    private static final String VALID_GOAL_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_RECIPE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_RECIPE, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedTime = new Time(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        Time expectedTime = new Time(VALID_TIME);
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parseStep_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStep((String) null));
    }

    @Test
    public void parseStep_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStep(INVALID_STEP));
    }

    @Test
    public void parseStep_validValueWithoutWhitespace_returnsStep() throws Exception {
        Step expectedStep = new Step(VALID_STEP);
        assertEquals(expectedStep, ParserUtil.parseStep(VALID_STEP));
    }

    @Test
    public void parseStep_validValueWithWhitespace_returnsTrimmedStep() throws Exception {
        String stepWithWhitespace = WHITESPACE + VALID_STEP + WHITESPACE;
        Step expectedStep = new Step(VALID_STEP);
        assertEquals(expectedStep, ParserUtil.parseStep(stepWithWhitespace));
    }

    @Test
    public void parseGoal_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGoal(null));
    }

    @Test
    public void parseGoal_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGoal(INVALID_GOAL));
    }

    @Test
    public void parseGoal_validValueWithoutWhitespace_returnsGoal() throws Exception {
        Goal expectedGoal = new Goal(VALID_GOAL_1);
        assertEquals(expectedGoal, ParserUtil.parseGoal(VALID_GOAL_1));
    }

    @Test
    public void parseGoal_validValueWithWhitespace_returnsTrimmedGoal() throws Exception {
        String goalWithWhitespace = WHITESPACE + VALID_GOAL_1 + WHITESPACE;
        Goal expectedGoal = new Goal(VALID_GOAL_1);
        assertEquals(expectedGoal, ParserUtil.parseGoal(goalWithWhitespace));
    }

    @Test
    public void parseGoals_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGoals(null));
    }

    @Test
    public void parseGoals_collectionWithInvalidGoals_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGoals(Arrays.asList(VALID_GOAL_1, INVALID_GOAL)));
    }

    @Test
    public void parseGoals_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseGoals(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseGoals_collectionWithValidGoals_returnsGoalSet() throws Exception {
        Set<Goal> actualGoalSet = ParserUtil.parseGoals(Arrays.asList(VALID_GOAL_1, VALID_GOAL_2));
        Set<Goal> expectedGoalSet = new HashSet<Goal>(Arrays.asList(new Goal(VALID_GOAL_1), new Goal(VALID_GOAL_2)));

        assertEquals(expectedGoalSet, actualGoalSet);
    }
}

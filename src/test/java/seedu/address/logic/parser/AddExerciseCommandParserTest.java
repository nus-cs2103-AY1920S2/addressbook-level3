package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.EXERCISE_DATE_DESC_PUSHUP;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.EXERCISE_NAME_DESC_PUSHUP;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.INVALID_EXERCISE_DATE_DESC;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.INVALID_EXERCISE_NAME_DESC;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.INVALID_EXERCISE_REPS_DESC;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.INVALID_EXERCISE_SETS_DESC;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.INVALID_EXERCISE_WEIGHT_DESC;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.VALID_EXERCISE_DATE_PUSHUP;
import static seedu.address.logic.commands.ExerciseCommandTestUtil.VALID_EXERCISE_NAME_PUSHUP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddExerciseCommand;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseDate;
import seedu.address.model.exercise.ExerciseName;
import seedu.address.model.exercise.ExerciseReps;
import seedu.address.model.exercise.ExerciseSets;
import seedu.address.model.exercise.ExerciseWeight;
import seedu.address.testutil.ExerciseBuilder;

public class AddExerciseCommandParserTest {

    private static final String EMPTY_FIELD = "";

    private AddExerciseCommandParser parser = new AddExerciseCommandParser();

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Exercise expectedClient = new ExerciseBuilder()
                .withExerciseName(VALID_EXERCISE_NAME_PUSHUP)
                .withExerciseDate(VALID_EXERCISE_DATE_PUSHUP)
                .withExerciseReps(EMPTY_FIELD)
                .withExerciseSets(EMPTY_FIELD)
                .withExerciseWeight(EMPTY_FIELD)
                .build();
        assertParseSuccess(parser, EXERCISE_NAME_DESC_PUSHUP + EXERCISE_DATE_DESC_PUSHUP,
                new AddExerciseCommand(expectedClient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE);

        // missing exercise date
        assertParseFailure(parser, VALID_EXERCISE_NAME_PUSHUP, expectedMessage);

        // missing exercise name
        assertParseFailure(parser, VALID_EXERCISE_DATE_PUSHUP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EXERCISE_NAME_DESC + EXERCISE_DATE_DESC_PUSHUP,
                ExerciseName.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EXERCISE_NAME_DESC_PUSHUP + INVALID_EXERCISE_DATE_DESC,
                ExerciseDate.MESSAGE_CONSTRAINTS);

        // invalid reps
        assertParseFailure(parser, EXERCISE_NAME_DESC_PUSHUP + EXERCISE_DATE_DESC_PUSHUP
                + INVALID_EXERCISE_REPS_DESC, ExerciseReps.MESSAGE_CONSTRAINTS);

        // invalid weight
        assertParseFailure(parser, EXERCISE_NAME_DESC_PUSHUP + EXERCISE_DATE_DESC_PUSHUP
                + INVALID_EXERCISE_WEIGHT_DESC, ExerciseWeight.MESSAGE_CONSTRAINTS);

        // invalid sets
        assertParseFailure(parser, EXERCISE_NAME_DESC_PUSHUP + EXERCISE_DATE_DESC_PUSHUP
                + INVALID_EXERCISE_SETS_DESC, ExerciseSets.MESSAGE_CONSTRAINTS);
    }
}

package seedu.zerotoone.logic.parser.exercise;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseFailure;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseSuccess;
import static seedu.zerotoone.testutil.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.zerotoone.testutil.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.EXERCISE_NAME_DESC_BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.INVALID_EXERCISE_NAME_DESC;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.VALID_EXERCISE_NAME_BENCH_PRESS;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.exercise.CreateCommand;
import seedu.zerotoone.model.exercise.ExerciseName;

public class CreateCommandParserTest {
    private CreateCommandParser parser = new CreateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ExerciseName expectedExerciseName = new ExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EXERCISE_NAME_DESC_BENCH_PRESS,
                new CreateCommand(expectedExerciseName));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE);

        // missing exercise name prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EXERCISE_NAME_DESC, ExerciseName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EXERCISE_NAME_DESC_BENCH_PRESS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
    }
}

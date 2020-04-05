package seedu.zerotoone.logic.parser.workout;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseFailure;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseSuccess;
import static seedu.zerotoone.testutil.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.zerotoone.testutil.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.INVALID_WORKOUT_NAME_DESC;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_ABS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.WORKOUT_NAME_DESC_ABS_WORKOUT;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.workout.CreateCommand;
import seedu.zerotoone.model.workout.WorkoutName;

public class CreateCommandParserTest {
    private CreateCommandParser parser = new CreateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        WorkoutName expectedWorkoutName = new WorkoutName(VALID_WORKOUT_NAME_ABS_WORKOUT);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + WORKOUT_NAME_DESC_ABS_WORKOUT,
                new CreateCommand(expectedWorkoutName));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE);

        // missing workout name prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_WORKOUT_NAME_DESC, WorkoutName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + WORKOUT_NAME_DESC_ABS_WORKOUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
    }
}

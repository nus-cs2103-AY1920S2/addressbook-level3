package seedu.zerotoone.logic.parser.workout;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.util.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseFailure;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseSuccess;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.INVALID_WORKOUT_NAME_DESC;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_ABS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.WORKOUT_NAME_DESC_ABS_WORKOUT;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.workout.EditCommand;
import seedu.zerotoone.model.workout.WorkoutName;

public class EditCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_WORKOUT_NAME_ABS_WORKOUT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + WORKOUT_NAME_DESC_ABS_WORKOUT, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + WORKOUT_NAME_DESC_ABS_WORKOUT, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_WORKOUT_NAME_DESC,
                WorkoutName.MESSAGE_CONSTRAINTS); // invalid workout name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_OBJECT;
        String userInput = targetIndex.getOneBased() + WORKOUT_NAME_DESC_ABS_WORKOUT;
        EditCommand expectedCommand = new EditCommand(targetIndex,
                new WorkoutName(VALID_WORKOUT_NAME_ABS_WORKOUT));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

package seedu.zerotoone.logic.parser.exercise;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.util.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseFailure;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseSuccess;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.EXERCISE_NAME_DESC_BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.INVALID_EXERCISE_NAME_DESC;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.VALID_EXERCISE_NAME_BENCH_PRESS;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.exercise.EditCommand;
import seedu.zerotoone.model.exercise.ExerciseName;

public class EditCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EXERCISE_NAME_BENCH_PRESS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EXERCISE_NAME_DESC_BENCH_PRESS, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + EXERCISE_NAME_DESC_BENCH_PRESS, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_EXERCISE_NAME_DESC,
                ExerciseName.MESSAGE_CONSTRAINTS); // invalid exercise name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EXERCISE;
        String userInput = targetIndex.getOneBased() + EXERCISE_NAME_DESC_BENCH_PRESS;
        EditCommand expectedCommand = new EditCommand(targetIndex,
                new ExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

package seedu.recipe.logic.parser.plan;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_DATE_FUTURE;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_DATE_PAST;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_DATE_FUTURE;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.plan.PlanCommand;
import seedu.recipe.logic.parser.ParserUtil;
import seedu.recipe.model.Date;

public class PlanCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_USAGE);

    private static final String MESSAGE_INVALID_DATE_IN_PAST =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlanCommand.MESSAGE_INVALID_DATE);

    private static final Index[] VALID_INDEXES = new Index[] {INDEX_FIRST_RECIPE};
    private static final Index[] VALID_MULTIPLE_INDEXES =
            new Index[] {INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE, INDEX_THIRD_RECIPE};

    private PlanCommandParser parser = new PlanCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DESC_DATE_FUTURE, MESSAGE_INVALID_FORMAT);

        // no date specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and date specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {

        // negative index
        assertParseFailure(parser, "-1" + DESC_DATE_FUTURE, ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + DESC_DATE_FUTURE, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid values
        assertParseFailure(parser, "?" + DESC_DATE_FUTURE, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + DESC_DATE_FUTURE, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + DESC_DATE_FUTURE, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidDate_failure() {
        // date in the past
        assertParseFailure(parser, "1" + DESC_DATE_PAST, MESSAGE_INVALID_DATE_IN_PAST);

        // invalid format
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);
    }


    @Test
    public void parse_validValues_success() {
        Index validIndex = VALID_INDEXES[0];

        String userInput = validIndex.getOneBased() + DESC_DATE_FUTURE;

        Date date = new Date(VALID_DATE_FUTURE);
        PlanCommand expectedCommand = new PlanCommand(VALID_INDEXES, date);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validValuesMultipleIndexes_success() {
        String userInputForIndex = Arrays.stream(VALID_MULTIPLE_INDEXES)
                .map(index -> index.getOneBased())
                .map(indexInt -> indexInt.toString())
                .collect(Collectors.joining(" "));

        String userInput = userInputForIndex + DESC_DATE_FUTURE;

        Date date = new Date(VALID_DATE_FUTURE);
        PlanCommand expectedCommand = new PlanCommand(VALID_MULTIPLE_INDEXES, date);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}

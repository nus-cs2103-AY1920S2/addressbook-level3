package seedu.recipe.logic.parser.plan;

import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.plan.DeletePlanCommand;

public class DeletePlanCommandParserTest {

    private static final Index[] VALID_INDEXES = new Index[] {INDEX_FIRST_RECIPE};
    private static final Index[] VALID_MULTIPLE_INDEXES =
            new Index[] {INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE, INDEX_THIRD_RECIPE};

    private DeletePlanCommandParser parser = new DeletePlanCommandParser();

    @Test
    public void parse_invalidIndexes_failure() {
        // no index specified
        assertParseFailure(parser, "", MESSAGE_INVALID_INDEX);

        // invalid index specified
        assertParseFailure(parser, "-1", MESSAGE_INVALID_INDEX);

        // valid then invalid index specified
        assertParseFailure(parser, "1 -1", MESSAGE_INVALID_INDEX);

        // invalid values
        assertParseFailure(parser, "??", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validValues_success() {
        Index validIndex = VALID_INDEXES[0];
        String userInput = "" + validIndex.getOneBased();

        DeletePlanCommand expectedCommad = new DeletePlanCommand(VALID_INDEXES);
        assertParseSuccess(parser, userInput, expectedCommad);
    }

    @Test
    public void parse_multipleValidValues_success() {
        String userInput = Arrays.stream(VALID_MULTIPLE_INDEXES)
                .map(index -> index.getOneBased())
                .map(indexInt -> indexInt.toString())
                .collect(Collectors.joining(" "));

        DeletePlanCommand expectedCommad = new DeletePlanCommand(VALID_MULTIPLE_INDEXES);
        assertParseSuccess(parser, userInput, expectedCommad);
    }

}

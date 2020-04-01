package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.DeleteStepCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteStepCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteStepCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteStepCommandParserTest {

    private DeleteStepCommandParser parser = new DeleteStepCommandParser();
    private final Integer[] validStepIndex = new Integer[] {1};

    @Test
    public void parse_validArgs_returnsDeleteStepCommand() {
        assertParseSuccess(parser, "2 2", new DeleteStepCommand(INDEX_SECOND_RECIPE, validStepIndex));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a 1", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_insufficientArgs_throwsParseException() {
        assertParseFailure(parser, "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStepCommand.MESSAGE_USAGE));
    }
}

package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.EditStepCommand;
import seedu.recipe.model.recipe.Step;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the EditStepCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the EditStepCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class EditStepCommandParserTest {

    private EditStepCommandParser parser = new EditStepCommandParser();
    private final int indexFirstStep = 0; // Steps are zero-indexed by design
    private final Step editedStep = new Step("Edited step");

    @Test
    public void parse_validArgs_returnsEditStepCommand() {
        assertParseSuccess(parser, "2 1 s/Edited step",
                new EditStepCommand(INDEX_SECOND_RECIPE, indexFirstStep, editedStep));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a 1 s/Edited step", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_insufficientArgs_throwsParseException() {
        assertParseFailure(parser, "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStepCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStepField_throwsParseException() {
        assertParseFailure(parser, "2 1 s/", Step.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleStepFields_returnsEditStepCommand() {
        assertParseSuccess(parser, "2 1 s/Edited step s/Ignored step",
                new EditStepCommand(INDEX_SECOND_RECIPE, indexFirstStep, editedStep));
    }
}

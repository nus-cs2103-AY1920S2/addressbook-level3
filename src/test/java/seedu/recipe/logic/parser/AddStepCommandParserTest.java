package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.AddStepCommand;
import seedu.recipe.model.recipe.Step;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the AddStepCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the AddStepCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class AddStepCommandParserTest {

    private AddStepCommandParser parser = new AddStepCommandParser();
    private final Step newStep = new Step("New step");

    @Test
    public void parse_validArgs_returnsDeleteStepCommand() {
        List<Step> newStepList = new ArrayList<>();
        newStepList.add(newStep);
        assertParseSuccess(parser, "2 s/New step", new AddStepCommand(INDEX_SECOND_RECIPE, newStepList));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a s/New step", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidStep_throwsParseException() {
        assertParseFailure(parser, "2 s/", Step.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_insufficientArgs_throwsParseException() {
        assertParseFailure(parser, "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStepCommand.MESSAGE_USAGE));
    }
}

package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.recipe.DeleteGoalCommand;

class DeleteGoalCommandParserTest {

    private DeleteGoalCommandParser parser = new DeleteGoalCommandParser();
    private final String validGoalName = "Herbivore";

    @Test
    public void parse_validArgs_returnsDeleteGoalCommand() {
        assertParseSuccess(parser, "2 Herbivore", new DeleteGoalCommand(INDEX_SECOND_RECIPE, validGoalName));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a 1", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_insufficientArgs_throwsParseException() {
        assertParseFailure(parser, "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoalCommand.MESSAGE_USAGE));
    }
}

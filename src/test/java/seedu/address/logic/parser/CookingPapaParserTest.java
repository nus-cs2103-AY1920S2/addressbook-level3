package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.cookbook.CookbookAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.RecipeBuilder;

public class CookingPapaParserTest {

    private final CookingPapaParser parser = new CookingPapaParser();

    @Test
    public void parseCommand_cookbookAdd() throws Exception {
        Recipe recipe = new RecipeBuilder().withRecipeName("Recipe").withRecipeDescription("test").build();
        CookbookAddCommand command = (CookbookAddCommand) parser.parseCommand("cookbook add recipe n/Recipe d/test");
        assertEquals(new CookbookAddCommand(recipe), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_CATEGORY) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_CATEGORY + "") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_CATEGORY) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_CATEGORY + "") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

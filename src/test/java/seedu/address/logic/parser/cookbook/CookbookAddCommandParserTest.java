package seedu.address.logic.parser.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cookbook.CookbookAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.TypicalRecipes;

public class CookbookAddCommandParserTest {
    private static final Recipe VALID_RECIPE = TypicalRecipes.getValidRecipe();
    private static final String VALID_ARGUMENT = "recipe n/Recipe d/Description i/Ingredient q/5 s/Step t/Tag";
    private static final String INVALID_ARGUMENT_NO_RECIPE_NAME = "recipe d/Description i/Ingredient q/5 x/1 "
            + "s/Step t/Tag";
    private static final String INVALID_ARGUMENT_NO_RECIPE_DESCRIPTION = "recipe n/Recipe i/Ingredient q/5 "
            + "x/1 s/Step t/Tag";
    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        CookbookAddCommandParser c = new CookbookAddCommandParser();
        assertEquals(c.parse(VALID_ARGUMENT), new CookbookAddCommand(VALID_RECIPE));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        CookbookAddCommandParser c = new CookbookAddCommandParser();
        assertThrows(NullPointerException.class, () -> c.parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () ->
            new CookbookAddCommandParser().parse(INVALID_ARGUMENT_NO_RECIPE_NAME));
        assertThrows(ParseException.class, () ->
                new CookbookAddCommandParser().parse(INVALID_ARGUMENT_NO_RECIPE_DESCRIPTION));
        assertThrows(ParseException.class, () ->
                new CookbookAddCommandParser().parse(INVALID_ARGUMENT));
    }
}

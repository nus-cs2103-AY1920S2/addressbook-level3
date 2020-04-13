package seedu.address.logic.parser.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cookbook.CookbookAddCommand;
import seedu.address.logic.commands.cookbook.CookbookRemoveCommand;
import seedu.address.logic.commands.cookbook.CookbookViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.TypicalRecipes;

public class CookbookCommandParserTest {
    private static final Index VALID_INDEX = new Index(0);
    private static final String VALID_ADD_ARGUMENT = " add recipe n/Recipe "
            + "d/Description i/Ingredient q/5 s/Step t/Tag";
    private static final String VALID_VIEW_INDEX_STRING = " view recipe 1";
    private static final String VALID_REMOVE_INDEX_STRING = " remove recipe 1";
    private static final Recipe VALID_RECIPE = TypicalRecipes.getValidRecipe();
    private static final String INVALID_ARGUMENT_NO_RECIPE_NAME = " add recipe d/Description i/Ingredient q/5 "
            + "s/Step t/Tag";
    private static final String INVALID_ARGUMENT_NO_RECIPE_DESCRIPTION = " add recipe n/Recipe i/Ingredient q/5 "
            + "s/Step t/Tag";
    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        CookbookCommandParser c = new CookbookCommandParser();
        assertEquals(c.parse(VALID_ADD_ARGUMENT), new CookbookAddCommand(VALID_RECIPE));
        assertEquals(c.parse(VALID_REMOVE_INDEX_STRING), new CookbookRemoveCommand(VALID_INDEX));
        assertEquals(c.parse(VALID_VIEW_INDEX_STRING), new CookbookViewCommand(VALID_INDEX));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        CookbookCommandParser c = new CookbookCommandParser();
        assertThrows(NullPointerException.class, () -> c.parse(null));
    }

    @Test
    public void parse_invalidInput() {
        CookbookCommandParser c = new CookbookCommandParser();
        assertThrows(ParseException.class, () -> new CookbookCommandParser().parse(INVALID_ARGUMENT));
        assertThrows(ParseException.class, () -> new CookbookCommandParser().parse(INVALID_ARGUMENT_NO_RECIPE_NAME));
        assertThrows(ParseException.class, () ->
                new CookbookCommandParser().parse(INVALID_ARGUMENT_NO_RECIPE_DESCRIPTION));
    }
}

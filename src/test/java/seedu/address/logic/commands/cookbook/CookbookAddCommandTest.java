package seedu.address.logic.commands.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CookbookAddCommandTest {

    @Test
    public void constructor_validInput() {
        CookbookAddCommand c = new CookbookAddCommand(AGLIO_OLIO);
        assertEquals(c, new CookbookAddCommand(AGLIO_OLIO));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookbookAddCommand(null));
    }

    @Test
    public void execute_validInput() {
        CookbookAddCommand c = new CookbookAddCommand(AGLIO_OLIO);
        Model model = new ModelManager();
        // assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, AGLIO_OLIO, 0)));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        CookbookAddCommand c = new CookbookAddCommand(AGLIO_OLIO);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void equalsMethod() {
        CookbookAddCommand c = new CookbookAddCommand(AGLIO_OLIO);
        assertEquals(c, new CookbookAddCommand(AGLIO_OLIO));
        assertNotEquals(c, new CookbookAddCommand(CARBONARA));
        assertNotEquals(c, null);
    }
}

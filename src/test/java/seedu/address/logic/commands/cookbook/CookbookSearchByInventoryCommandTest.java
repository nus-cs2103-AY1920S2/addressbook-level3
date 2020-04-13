package seedu.address.logic.commands.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_RECIPES_LISTED_OVERVIEW;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CookbookSearchByInventoryCommandTest {
    @Test
    public void execute_validInput() {
        Model model = new ModelManager();
        assertEquals(new CookbookSearchByInventoryCommand().execute(model),
            new CommandResult(String.format(MESSAGE_RECIPES_LISTED_OVERVIEW,
            model.getFilteredCartIngredientList().size())));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CookbookSearchByInventoryCommand().execute(null));
    }

    @Test
    public void execute_success() {

    }

    @Test
    public void equalsMethod() {
        assertEquals(new CookbookSearchByInventoryCommand(), new CookbookSearchByInventoryCommand());
        assertNotEquals(new CookbookSearchByInventoryCommand(), null);
    }
}

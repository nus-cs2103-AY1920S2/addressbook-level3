package seedu.address.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.recipe.RecipeAddTagCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.tag.Tag;

public class RecipeAddTagCommandTest {
    private static final Tag VALID_TAG_ONE = new Tag("Easy");
    private static final Tag VALID_TAG_TWO = new Tag("Difficult");
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(1);

    @Test
    public void constructor_validInput() {
        RecipeAddTagCommand c = new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        assertEquals(c, new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeAddTagCommand(null, VALID_TAG_ONE));
        assertThrows(NullPointerException.class, () -> new RecipeAddTagCommand(VALID_RECIPE_INDEX, null));
        assertThrows(NullPointerException.class, () -> new RecipeAddTagCommand(null, null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        RecipeAddTagCommand c = new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        Model model = new ModelManager();
        model.addCookbookRecipe(CARBONARA);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, VALID_TAG_ONE)));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        RecipeAddTagCommand c = new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void execute_invalidInput() throws CommandException {
        RecipeAddTagCommand c = new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        Model model = new ModelManager();

        // adding to a non-existent recipe
        assertThrows(CommandException.class, () -> c.execute(model));

        model.addCookbookRecipe(CARBONARA);

        // recipe index out of bounds
        RecipeAddTagCommand d = new RecipeAddTagCommand(OUT_OF_BOUNDS_RECIPE_INDEX, VALID_TAG_ONE);
        assertThrows(CommandException.class, () -> d.execute(model));

        // adding duplicate tag
        RecipeAddTagCommand f = new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_TWO);
        f.execute(model);
        assertThrows(CommandException.class, () -> f.execute(model));
    }

    @Test
    public void equalsMethod() {
        RecipeAddTagCommand c = new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        assertEquals(c, new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE));
        assertNotEquals(c, null);
    }

}

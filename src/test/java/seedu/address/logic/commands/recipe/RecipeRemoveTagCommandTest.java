package seedu.address.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.recipe.RecipeRemoveTagCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.tag.Tag;

public class RecipeRemoveTagCommandTest {
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(1);
    private static final Tag VALID_TAG_ONE = new Tag("Easy");
    private static final Tag VALID_TAG_TWO = new Tag("Difficult");

    @Test
    public void constructor_validInput() {
        RecipeRemoveTagCommand c = new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        assertEquals(c, new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeRemoveTagCommand(VALID_RECIPE_INDEX,
                null));
        assertThrows(NullPointerException.class, () -> new RecipeRemoveTagCommand(null,
                VALID_TAG_ONE));
        assertThrows(NullPointerException.class, () -> new RecipeRemoveTagCommand(null,
                null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        RecipeRemoveTagCommand c = new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        Model model = new ModelManager();
        model.addCookbookRecipe(AGLIO_OLIO);

        // add a tag to recipe
        new RecipeAddTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE).execute(model);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS, VALID_TAG_ONE)));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        RecipeRemoveTagCommand c = new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void execute_invalidInput() {
        RecipeRemoveTagCommand c = new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_TWO);
        Model model = new ModelManager();

        // removing from a non-existent recipe
        assertThrows(CommandException.class, () -> c.execute(model));

        model.addCookbookRecipe(AGLIO_OLIO);

        // removing a non-existent tag
        assertThrows(CommandException.class, () -> c.execute(model));

        // recipe index out of bounds
        RecipeRemoveTagCommand d = new RecipeRemoveTagCommand(OUT_OF_BOUNDS_RECIPE_INDEX, VALID_TAG_TWO);
        assertThrows(CommandException.class, () -> d.execute(model));
    }

    @Test
    public void equalsMethod() {
        RecipeRemoveTagCommand c = new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE);
        assertEquals(c, new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_ONE));
        assertNotEquals(c, new RecipeRemoveTagCommand(VALID_RECIPE_INDEX, VALID_TAG_TWO));
        assertNotEquals(c, null);
    }
}

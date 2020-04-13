package seedu.address.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.recipe.RecipeRemoveStepCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalSteps.CARBONARA_ONE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Cookbook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class RecipeRemoveStepCommandTest {
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(1);
    private static final Index VALID_REMOVE_STEP_INDEX = new Index(0);
    private static final Optional<Index> VALID_ADD_STEP_INDEX = Optional.of(new Index(0));
    private static final Index OUT_OF_BOUNDS_STEP_INDEX = new Index(1);

    @Test
    public void constructor_validInput() {
        RecipeRemoveStepCommand c = new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX);
        assertEquals(c, new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeRemoveStepCommand(VALID_RECIPE_INDEX,
                null));
        assertThrows(NullPointerException.class, () -> new RecipeRemoveStepCommand(null,
                VALID_REMOVE_STEP_INDEX));
        assertThrows(NullPointerException.class, () -> new RecipeRemoveStepCommand(null,
                null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        RecipeRemoveStepCommand c = new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX);
        Model model = new ModelManager();
        model.setCookbook(new Cookbook());
        model.addCookbookRecipe(AGLIO_OLIO);

        // add a step to recipe
        new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_ADD_STEP_INDEX, CARBONARA_ONE).execute(model);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_SUCCESS,
                AGLIO_OLIO.getName().fullRecipeName, CARBONARA_ONE)));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        RecipeRemoveStepCommand c = new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void execute_invalidInput() {
        RecipeRemoveStepCommand c = new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX);
        Model model = new ModelManager();

        // removing from a non-existent recipe
        assertThrows(CommandException.class, () -> c.execute(model));

        model.addCookbookRecipe(AGLIO_OLIO);

        // recipe index out of bounds
        RecipeRemoveStepCommand d = new RecipeRemoveStepCommand(OUT_OF_BOUNDS_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX);
        assertThrows(CommandException.class, () -> d.execute(model));
    }

    @Test
    public void equalsMethod() {
        RecipeRemoveStepCommand c = new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX);
        assertEquals(c, new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, VALID_REMOVE_STEP_INDEX));
        assertNotEquals(c, new RecipeRemoveStepCommand(VALID_RECIPE_INDEX, OUT_OF_BOUNDS_STEP_INDEX));
        assertNotEquals(c, null);
    }
}

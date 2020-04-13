package seedu.address.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.recipe.RecipeAddStepCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;
import static seedu.address.testutil.TypicalSteps.CARBONARA_ONE;
import static seedu.address.testutil.TypicalSteps.CARBONARA_TWO;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class RecipeAddStepCommandTest {
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Optional<Index> VALID_STEP_INDEX = Optional.of(new Index(0));
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(1);
    private static final Optional<Index> OUT_OF_BOUNDS_STEP_INDEX = Optional.of(new Index(1));

    @Test
    public void constructor_validInput() {
        RecipeAddStepCommand c = new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE);
        assertEquals(c, new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeAddStepCommand(VALID_RECIPE_INDEX, null, null));
        assertThrows(NullPointerException.class, () -> new RecipeAddStepCommand(null, VALID_STEP_INDEX, null));
        assertThrows(NullPointerException.class, () -> new RecipeAddStepCommand(null, null, CARBONARA_ONE));
        assertThrows(NullPointerException.class, () -> new RecipeAddStepCommand(null, null, null));
    }

    @Test
    public void execute_validInput() throws CommandException {
        RecipeAddStepCommand c = new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE);

        Model model = new ModelManager();
        model.addCookbookRecipe(CARBONARA);

        CommandResult one = c.execute(model);
        CommandResult two = new CommandResult(String.format(MESSAGE_SUCCESS,
                CARBONARA.getName().fullRecipeName, CARBONARA_ONE));

        assertEquals(one, two);
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        RecipeAddStepCommand c = new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE);
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void execute_invalidInput() throws CommandException {
        RecipeAddStepCommand c = new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE);
        Model model = new ModelManager();

        // adding to a non-existent recipe
        assertThrows(CommandException.class, () -> c.execute(model));

        model.addCookbookRecipe(CARBONARA);

        // step index out of bounds
        RecipeAddStepCommand d = new RecipeAddStepCommand(VALID_RECIPE_INDEX, OUT_OF_BOUNDS_STEP_INDEX, CARBONARA_ONE);
        assertThrows(CommandException.class, () -> d.execute(model));

        // recipe index out of bounds
        RecipeAddStepCommand e = new RecipeAddStepCommand(OUT_OF_BOUNDS_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE);
        assertThrows(CommandException.class, () -> e.execute(model));

        // adding duplicate step
        RecipeAddStepCommand f = new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_TWO);
        f.execute(model);
        assertThrows(CommandException.class, () -> f.execute(model));
    }

    @Test
    public void equalsMethod() {
        RecipeAddStepCommand c = new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE);
        assertEquals(c, new RecipeAddStepCommand(VALID_RECIPE_INDEX, VALID_STEP_INDEX, CARBONARA_ONE));
        assertNotEquals(c, new RecipeAddStepCommand(VALID_RECIPE_INDEX, OUT_OF_BOUNDS_STEP_INDEX, CARBONARA_ONE));
        assertNotEquals(c, null);
    }
}

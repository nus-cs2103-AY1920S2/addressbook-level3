package seedu.address.logic.commands.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIngredients.BUTTER;
import static seedu.address.testutil.TypicalIngredients.EGG;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class RecipeRemoveIngredientCommandTest {
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final Index OUT_OF_BOUNDS_RECIPE_INDEX = new Index(1);
    private static final IngredientName VALID_INGREDIENT_NAME = EGG.getName();
    private static final IngredientQuantity VALID_INGREDIENT_QUANTITY = new IngredientQuantity("2");
    private static final Ingredient VALID_INGREDIENT = new Ingredient(VALID_INGREDIENT_NAME,
            VALID_INGREDIENT_QUANTITY);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                null, null)); // index, null, null
        assertThrows(NullPointerException.class, () -> new RecipeRemoveIngredientCommand(null,
                null, Optional.of(VALID_INGREDIENT_QUANTITY))); // null, null, quantity
        assertThrows(NullPointerException.class, () -> new RecipeRemoveIngredientCommand(null,
                VALID_INGREDIENT_NAME, null)); // null, name, null
        assertThrows(NullPointerException.class, () -> new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                VALID_INGREDIENT_NAME, null)); // index, name, null
        assertThrows(NullPointerException.class, () -> new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                null, Optional.of(VALID_INGREDIENT_QUANTITY))); // index, null, quantity
        assertThrows(NullPointerException.class, () -> new RecipeRemoveIngredientCommand(null,
                VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY))); // null, name, quantity
        assertThrows(NullPointerException.class, () -> new RecipeRemoveIngredientCommand(null, null, null)); // all null
    }

    @Test
    public void execute_validInput() throws CommandException {
        RecipeRemoveIngredientCommand commandWithQuantity = new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY));
        RecipeRemoveIngredientCommand commandWithoutQuantity = new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                VALID_INGREDIENT_NAME, Optional.empty());
        Model model = new ModelManager();
        model.addCookbookRecipe(CARBONARA);


        // removing existing ingredient with quantity specified
        assertEquals(commandWithQuantity.execute(model),
                new CommandResult(String.format(RecipeRemoveIngredientCommand.MESSAGE_SUCCESS,
                    VALID_INGREDIENT, CARBONARA.getName())));

        // removing existing ingredient without quantity specified
        assertEquals(commandWithoutQuantity.execute(model),
                new CommandResult(String.format(RecipeRemoveIngredientCommand.MESSAGE_SUCCESS,
                    RecipeRemoveIngredientCommand.ALL_KEYWORD + " " + VALID_INGREDIENT_NAME, CARBONARA.getName())));

        // error thrown after removing EGG
        assertThrows(CommandException.class, () -> commandWithQuantity.execute(model));
        assertThrows(CommandException.class, () -> commandWithoutQuantity.execute(model));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        RecipeRemoveIngredientCommand c = new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX, VALID_INGREDIENT_NAME,
                Optional.of(VALID_INGREDIENT_QUANTITY));
        assertThrows(NullPointerException.class, () -> c.execute(null));
    }

    @Test
    public void execute_invalidInput() throws CommandException {
        RecipeRemoveIngredientCommand c = new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY));
        Model model = new ModelManager();

        // removing from a non-existent recipe
        assertThrows(CommandException.class, () -> c.execute(model));

        model.addCookbookRecipe(CARBONARA);

        // recipe index out of bounds
        RecipeRemoveIngredientCommand d = new RecipeRemoveIngredientCommand(OUT_OF_BOUNDS_RECIPE_INDEX,
                VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY));
        assertThrows(CommandException.class, () -> d.execute(model));

        // add ingredient to CARBONARA
        new RecipeAddIngredientCommand(VALID_RECIPE_INDEX, BUTTER).execute(model);

        // ingredient not found
        RecipeRemoveIngredientCommand e = new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                BUTTER.getName(), Optional.of(BUTTER.getQuantity()));
        e.execute(model);
        assertThrows(CommandException.class, () -> e.execute(model));
    }

    @Test
    public void equalsMethod() {
        RecipeRemoveIngredientCommand c = new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY));
        assertEquals(c, new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY)));
        assertNotEquals(c, new RecipeRemoveIngredientCommand(VALID_RECIPE_INDEX,
                BUTTER.getName(), Optional.of(BUTTER.getQuantity())));
        assertNotEquals(c, null);
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Cookbook;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /// Ingredients
    public static final String VALID_INGREDIENT_NAME_ALMOND = "Almonds";
    public static final String VALID_INGREDIENT_NAME_BANANA = "Bananas";
    public static final String VALID_INGREDIENT_QUANTITY_ALMOND = "50 g";
    public static final String VALID_INGREDIENT_QUANTITY_BANANA = "3";

    public static final String INGREDIENT_NAME_DESC_ALMOND = " " + PREFIX_INGREDIENT_NAME
            + VALID_INGREDIENT_NAME_ALMOND;
    public static final String INGREDIENT_NAME_DESC_BANANA = " " + PREFIX_INGREDIENT_NAME
            + VALID_INGREDIENT_NAME_BANANA;
    public static final String INGREDIENT_QUANTITY_DESC_ALMOND = " " + PREFIX_INGREDIENT_QUANTITY
            + VALID_INGREDIENT_QUANTITY_ALMOND;
    public static final String INGREDIENT_QUANTITY_DESC_BANANA = " " + PREFIX_INGREDIENT_QUANTITY
            + VALID_INGREDIENT_QUANTITY_BANANA;

    public static final String INVALID_INGREDIENT_NAME_DESC = " " + PREFIX_INGREDIENT_NAME
            + "R!ce"; // '!' not allowed in names
    public static final String INVALID_INGREDIENT_QUANTITY_DESC = " " + PREFIX_INGREDIENT_QUANTITY
            + "*halal"; // '*' not allowed in quantity

    /// Recipes
    public static final String VALID_RECIPE_NAME_HAMBURGER = "Hamburger";
    public static final String VALID_RECIPE_NAME_SALAD = "Salad";
    public static final String VALID_RECIPE_DESCRIPTION_HAMBURGER = "Juicy grilled beef patty with toasted buns";
    public static final String VALID_RECIPE_DESCRIPTION_SALAD = "Lettuce, spinach & guacamole.";
    public static final String VALID_TAG_BRUNCH = "Brunch";
    public static final String VALID_TAG_HEALTHY = "Healthy";

    public static final String RECIPE_NAME_DESC_HAMBURGER = " " + PREFIX_RECIPE_NAME + VALID_RECIPE_NAME_HAMBURGER;
    public static final String RECIPE_NAME_DESC_SALAD = " " + PREFIX_RECIPE_NAME + VALID_RECIPE_NAME_SALAD;
    public static final String RECIPE_DESCRIPTION_DESC_HAMBURGER = " "
            + PREFIX_RECIPE_DESCRIPTION + VALID_RECIPE_DESCRIPTION_HAMBURGER;
    public static final String RECIPE_DESCRIPTION_DESC_SALAD = " "
            + PREFIX_RECIPE_DESCRIPTION + VALID_RECIPE_DESCRIPTION_SALAD;
    public static final String TAG_DESC_BRUNCH = " " + PREFIX_TAG + VALID_TAG_BRUNCH;
    public static final String TAG_DESC_HEALTHY = " " + PREFIX_TAG + VALID_TAG_HEALTHY;

    public static final String INVALID_RECIPE_NAME_DESC = " " + PREFIX_RECIPE_NAME
            + " "; // recipe names cannot have whitespaces only
    public static final String INVALID_RECIPE_DESCRIPTION_DESC = " " + PREFIX_RECIPE_DESCRIPTION
            + " "; // recipe descriptions cannot have whitespaces only
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "p@sta"; // '@' not allowed in tags

    /// Miscellaneous
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Cookbook expectedCookbook = new Cookbook(actualModel.getCookbook());
        List<Recipe> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCookbookRecipeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCookbook, actualModel.getCookbook());
        assertEquals(expectedFilteredList, actualModel.getFilteredCookbookRecipeList());
    }


    /**
     * Updates {@code model}'s filtered list to show only the recipe at the given {@code targetIndex} in the
     * {@code model}'s cookbook.
     */
    public static void showRecipeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCookbookRecipeList().size());

        Recipe recipe = model.getFilteredCookbookRecipeList().get(targetIndex.getZeroBased());
        final String[] splitName = recipe.getName().fullRecipeName.split("\\s+");
        model.updateFilteredCookbookRecipeList(new RecipeNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCookbookRecipeList().size());
    }

}

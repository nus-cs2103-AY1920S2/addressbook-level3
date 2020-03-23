package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.RecipeBook;
import seedu.address.model.recipe.NameContainsKeywordsPredicate;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.EditRecipeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // _HAM_SANDWICH
    // _EGGS_ON_TOAST

    public static final String VALID_NAME_HAM_SANDWICH = "Ham Sandwich";
    public static final String VALID_NAME_EGGS_ON_TOAST = "Eggs on Toast";
    public static final String VALID_INGREDIENTS_HAM_SANDWICH = "bread, 2 slices; ham, 1 slice; cheese spread, 10g";
    public static final String VALID_INGREDIENTS_EGGS_ON_TOAST = "bread, 2 slices; egg, 1; pepper, 1/16 tsp";
    public static final String VALID_INSTRUCTIONS_HAM_SANDWICH = "spread cheese on both slices of bread; put the "
                                                                 + "slice of ham between bread; serve on plate";
    public static final String VALID_INSTRUCTIONS_EGGS_ON_TOAST = "toast the 2 slices of bread; scramble the eggs "
                                                                  + "with the pepper; put egg between bread; serve on"
                                                                  + " plate";
    public static final String VALID_CALORIE_HAM_SANDWICH = "300";
    public static final String VALID_CALORIE_EGGS_ON_TOAST = "200";
    public static final String VALID_SERVING_HAM_SANDWICH = "2";
    public static final String VALID_SERVING_EGGS_ON_TOAST = "1";
    public static final String VALID_RATING_HAM_SANDWICH = "4"; // Ratings: 0 - 5, 0 being no rating
    public static final String VALID_RATING_EGGS_ON_TOAST = "3";
    public static final String VALID_TAG_BREAKFAST = "breakfast";
    public static final String VALID_TAG_LUNCH = "lunch";
    public static final String VALID_TAG_DINNER = "dinner";


    public static final String NAME_DESC_HAM_SANDWICH = " " + PREFIX_NAME + VALID_NAME_HAM_SANDWICH;
    public static final String NAME_DESC_EGGS_ON_TOAST = " " + PREFIX_NAME + VALID_NAME_EGGS_ON_TOAST;
    public static final String INGREDIENTS_DESC_HAM_SANDWICH =
            " " + PREFIX_INGREDIENTS + VALID_INGREDIENTS_HAM_SANDWICH;
    public static final String INGREDIENTS_DESC_EGGS_ON_TOAST =
            " " + PREFIX_INGREDIENTS + VALID_INGREDIENTS_EGGS_ON_TOAST;
    public static final String INSTRUCTIONS_DESC_HAM_SANDWICH =
            " " + PREFIX_INSTRUCTIONS + VALID_INSTRUCTIONS_HAM_SANDWICH;
    public static final String INSTRUCTIONS_DESC_EGGS_ON_TOAST =
            " " + PREFIX_INSTRUCTIONS + VALID_INSTRUCTIONS_EGGS_ON_TOAST;
    public static final String CALORIE_DESC_HAM_SANDWICH = " " + PREFIX_CALORIE + VALID_CALORIE_HAM_SANDWICH;
    public static final String CALORIE_DESC_EGGS_ON_TOAST = " " + PREFIX_CALORIE + VALID_CALORIE_EGGS_ON_TOAST;
    public static final String SERVING_DESC_HAM_SANDWICH = " " + PREFIX_SERVING + VALID_CALORIE_HAM_SANDWICH;
    public static final String SERVING_DESC_EGGS_ON_TOAST = " " + PREFIX_SERVING + VALID_CALORIE_EGGS_ON_TOAST;

    // TODO: add RATING_DESC once Rating has been merged

    public static final String TAG_DESC_BREAKFAST = " " + PREFIX_TAG + VALID_TAG_BREAKFAST;
    public static final String TAG_DESC_LUNCH = " " + PREFIX_TAG + VALID_TAG_LUNCH;
    public static final String TAG_DESC_DINNER = " " + PREFIX_TAG + VALID_TAG_DINNER;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Ham Sandwich&"; // '&' not allowed in names
    public static final String INVALID_INGREDIENTS_DESC = " " + PREFIX_INGREDIENTS + " "; // ingredients can't be blank
    public static final String INVALID_INSTRUCTIONS_DESC = " " + PREFIX_INSTRUCTIONS + " "; // ingredients can't be " "
    public static final String INVALID_CALORIE_DESC = " " + PREFIX_CALORIE + "abc"; // calorie can't be numeric
    public static final String INVALID_SERVING_DESC = " " + PREFIX_SERVING + "xyz"; // serving can't be numeric

    // TODO: add INVALID_RATING once Rating has been merged

    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "lunch*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final ModifyCommand.EditRecipeDescriptor DESC_HAM_SANDWICH;
    public static final ModifyCommand.EditRecipeDescriptor DESC_EGGS_ON_TOAST;

    static {
        DESC_HAM_SANDWICH = new EditRecipeDescriptorBuilder().withName(VALID_NAME_HAM_SANDWICH).withTags(
                VALID_TAG_LUNCH).build();
        DESC_EGGS_ON_TOAST = new EditRecipeDescriptorBuilder().withName(VALID_NAME_EGGS_ON_TOAST).withTags(
                VALID_TAG_BREAKFAST, VALID_TAG_LUNCH).build();
    }

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
     * - the recipe book, filtered recipe list and selected recipe in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RecipeBook expectedRecipeBook = new RecipeBook(actualModel.getRecipeBook());
        List<Recipe> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecipeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedRecipeBook, actualModel.getRecipeBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredRecipeList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the recipe at the given {@code targetIndex} in the
     * {@code model}'s recipe book.
     */
    public static void showRecipeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecipeList().size());

        Recipe recipe = model.getFilteredRecipeList().get(targetIndex.getZeroBased());
        final String[] splitName = recipe.getName().name.split("\\s+");
        model.updateFilteredRecipeList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecipeList().size());
    }

}

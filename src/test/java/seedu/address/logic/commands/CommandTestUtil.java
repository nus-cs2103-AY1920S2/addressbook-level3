package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
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

    public static final String VALID_FRUIT_TURKEY_SANDWICH = "300g, Tomato";
    public static final String VALID_FRUIT_FISH = "200g, Peach";
    public static final String VALID_GRAIN_TURKEY_SANDWICH = "300g, Wholemeal bread";
    public static final String VALID_GRAIN_FISH = "200g, Rice";
    public static final String VALID_PROTEIN_TURKEY_SANDWICH = "150g, Shredded Turkey";
    public static final String VALID_PROTEIN_FISH = "300g, Cod fish";
    public static final String VALID_VEGE_TURKEY_SANDWICH = "150g, Lettuce";
    public static final String VALID_VEGE_FISH = "300g, Tomato";
    public static final String VALID_OTHER_TURKEY_SANDWICH = "150g, Mayonnaise";
    public static final String VALID_OTHER_FISH = "300g, Sesame oil";
    public static final String VALID_NAME_TURKEY_SANDWICH = "Turkey Sandwich";
    public static final String VALID_NAME_FISH = "Steamed Fish";
    public static final String VALID_TIME_TURKEY_SANDWICH = "15";
    public static final String VALID_TIME_FISH = "40";
    public static final String VALID_STEP_TURKEY_SANDWICH = "Slice the turkey";
    public static final String VALID_STEP_FISH = "Remove fish stomach";
    public static final String VALID_GOAL_PROTEIN = "Bulk like the Hulk";
    public static final String VALID_GOAL_GRAIN = "Wholesome Wholemeal";

    public static final String FRUIT_DESC_TURKEY_SANDWICH = " " + PREFIX_INGREDIENT_FRUIT + VALID_FRUIT_TURKEY_SANDWICH;
    public static final String FRUIT_DESC_FISH = " " + PREFIX_INGREDIENT_FRUIT + VALID_FRUIT_FISH;
    public static final String GRAIN_DESC_TURKEY_SANDWICH = " " + PREFIX_INGREDIENT_GRAIN + VALID_GRAIN_TURKEY_SANDWICH;
    public static final String GRAIN_DESC_FISH = " " + PREFIX_INGREDIENT_GRAIN + VALID_GRAIN_FISH;
    public static final String NAME_DESC_TURKEY_SANDWICH = " " + PREFIX_NAME + VALID_NAME_TURKEY_SANDWICH;
    public static final String NAME_DESC_FISH = " " + PREFIX_NAME + VALID_NAME_FISH;
    public static final String OTHER_DESC_TURKEY_SANDWICH = " " + PREFIX_INGREDIENT_OTHER + VALID_OTHER_TURKEY_SANDWICH;
    public static final String OTHER_DESC_FISH = " " + PREFIX_INGREDIENT_OTHER + VALID_OTHER_FISH;
    public static final String PROTEIN_DESC_TURKEY_SANDWICH = " " + PREFIX_INGREDIENT_PROTEIN
            + VALID_PROTEIN_TURKEY_SANDWICH;
    public static final String PROTEIN_DESC_FISH = " " + PREFIX_INGREDIENT_PROTEIN + VALID_PROTEIN_FISH;
    public static final String TIME_DESC_TURKEY_SANDWICH = " " + PREFIX_TIME + VALID_TIME_TURKEY_SANDWICH;
    public static final String TIME_DESC_FISH = " " + PREFIX_TIME + VALID_TIME_FISH;
    public static final String VEGETABLE_DESC_TURKEY_SANDWICH = " " + PREFIX_INGREDIENT_VEGE
            + VALID_VEGE_TURKEY_SANDWICH;
    public static final String VEGETABLE_DESC_FISH = " " + PREFIX_INGREDIENT_VEGE + VALID_VEGE_FISH;
    public static final String STEP_DESC_TURKEY_SANDWICH = " " + PREFIX_STEP + VALID_STEP_TURKEY_SANDWICH;
    public static final String STEP_DESC_FISH = " " + PREFIX_STEP + VALID_STEP_FISH;
    public static final String GOAL_DESC_GRAIN = " " + PREFIX_GOAL + VALID_GOAL_GRAIN;
    public static final String GOAL_DESC_PROTEIN = " " + PREFIX_GOAL + VALID_GOAL_PROTEIN;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "911a"; // 'a' not allowed in times
    public static final String INVALID_STEP_DESC = " " + PREFIX_STEP + ""; // empty space
    public static final String INVALID_GOAL_DESC = " " + PREFIX_GOAL + "bulking*"; // '*' not allowed in goals

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditRecipeDescriptor DESC_TURKEY_SANDWICH;
    public static final EditCommand.EditRecipeDescriptor DESC_FISH;

    static {
        DESC_TURKEY_SANDWICH = new EditRecipeDescriptorBuilder().withName(VALID_NAME_TURKEY_SANDWICH)
                .withTime(VALID_TIME_TURKEY_SANDWICH).withGrains(VALID_GRAIN_TURKEY_SANDWICH)
                .withVegetables(VALID_VEGE_TURKEY_SANDWICH).withProteins(VALID_PROTEIN_TURKEY_SANDWICH)
                .withFruits(VALID_FRUIT_TURKEY_SANDWICH).withOthers(VALID_OTHER_TURKEY_SANDWICH)
                .withSteps(VALID_STEP_TURKEY_SANDWICH)
                .withGoals(VALID_GOAL_GRAIN).build();

        DESC_FISH = new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH)
                .withTime(VALID_TIME_FISH).withGrains(VALID_GRAIN_FISH)
                .withVegetables(VALID_VEGE_FISH).withProteins(VALID_PROTEIN_FISH)
                .withFruits(VALID_FRUIT_FISH).withOthers(VALID_OTHER_FISH)
                .withSteps(VALID_STEP_FISH)
                .withGoals(VALID_GOAL_PROTEIN).build();
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
     * - the address book, filtered recipe list and selected recipe in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RecipeBook expectedAddressBook = new RecipeBook(actualModel.getRecipeBook());
        List<Recipe> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecipeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getRecipeBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredRecipeList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the recipe at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showRecipeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecipeList().size());

        Recipe recipe = model.getFilteredRecipeList().get(targetIndex.getZeroBased());
        final String[] splitName = recipe.getName().fullName.split("\\s+");
        model.updateFilteredRecipeList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecipeList().size());
    }

}

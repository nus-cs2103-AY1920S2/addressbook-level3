package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.recipe.ClearCommand;
import seedu.recipe.logic.commands.recipe.EditCommand;
import seedu.recipe.logic.commands.recipe.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.cooked.CookedRecordBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.RecipeBook;
import seedu.recipe.testutil.EditRecipeDescriptorBuilder;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.ui.tab.Tab;

//import static seedu.recipe.logic.commands.CommandTestUtil.VALID_GOAL_PROTEIN;
//import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_FISH;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Recipe editedRecipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(editedRecipe).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs(),
                new CookedRecordBook(model.getRecordBook()), new PlannedBook(), new QuoteBook());

        expectedModel.setRecipe(model.getFilteredRecipeList().get(0), editedRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastRecipe = Index.fromOneBased(model.getFilteredRecipeList().size());
        Recipe lastRecipe = model.getFilteredRecipeList().get(indexLastRecipe.getZeroBased());

        RecipeBuilder recipeInList = new RecipeBuilder(lastRecipe);
        Recipe editedRecipe = recipeInList.withName(VALID_NAME_FISH).withTime(VALID_TIME_FISH).build();

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH)
                .withTime(VALID_TIME_FISH).build();
        EditCommand editCommand = new EditCommand(indexLastRecipe, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs(),
                 new CookedRecordBook(model.getRecordBook()), new PlannedBook(), new QuoteBook());

        expectedModel.setRecipe(lastRecipe, editedRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE, new EditRecipeDescriptor());
        Recipe editedRecipe = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs(),
                new CookedRecordBook(model.getRecordBook()), new PlannedBook(), new QuoteBook());

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Recipe recipeInFilteredList = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe editedRecipe = new RecipeBuilder(recipeInFilteredList).withName(VALID_NAME_FISH).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs(),
                new CookedRecordBook(model.getRecordBook()), new PlannedBook(), new QuoteBook());

        expectedModel.setRecipe(model.getFilteredRecipeList().get(0), editedRecipe);

        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateRecipeUnfilteredList_failure() {
        Recipe firstRecipe = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(firstRecipe).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_RECIPE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_duplicateRecipeFilteredList_failure() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        // edit recipe in filtered list into a duplicate in address book
        Recipe recipeInList = model.getRecipeBook().getRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder(recipeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_invalidRecipeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidRecipeIndexFilteredList_failure() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);
        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_RECIPE, DESC_TURKEY_SANDWICH);

        // same values -> returns true
        EditRecipeDescriptor copyDescriptor = new EditRecipeDescriptor(DESC_TURKEY_SANDWICH);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_RECIPE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_RECIPE, DESC_TURKEY_SANDWICH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_RECIPE, DESC_FISH)));
    }

}

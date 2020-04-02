package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AddIngredientCommand}.
 */
public class AddIngredientCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook());
    private final Other otherIngredientToAdd = RecipeBuilder.DEFAULT_OTHER;
    private final Protein proteinIngredientToAdd = RecipeBuilder.DEFAULT_PROTEIN;

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToAddIngredients = model.getFilteredRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> newOtherIngredients = new TreeSet<>();
        newOtherIngredients.add(otherIngredientToAdd);
        editRecipeDescriptor.setOthers(newOtherIngredients);

        AddIngredientCommand addIngredientCommand = new AddIngredientCommand(INDEX_SECOND_RECIPE, editRecipeDescriptor);

        String expectedMessageTemplate = "Successfully added ingredient(s) to %1$s!";
        String expectedMessage = String.format(expectedMessageTemplate, recipeToAddIngredients.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese", "100g, Oil")
                .withSteps("Spread butter on bread", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToAddIngredients, expectedRecipe);

        assertCommandSuccess(addIngredientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        AddIngredientCommand addIngredientCommand = new AddIngredientCommand(
                outOfBoundIndex, new EditRecipeDescriptor());

        assertCommandFailure(addIngredientCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Recipe recipeToAddIngredients = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> newOtherIngredients = new TreeSet<>();
        newOtherIngredients.add(otherIngredientToAdd);
        editRecipeDescriptor.setOthers(newOtherIngredients);

        AddIngredientCommand addIngredientCommand = new AddIngredientCommand(INDEX_FIRST_RECIPE, editRecipeDescriptor);

        String expectedMessageTemplate = "Successfully added ingredient(s) to %1$s!";
        String expectedMessage = String.format(expectedMessageTemplate, recipeToAddIngredients.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
        Recipe expectedRecipe = new RecipeBuilder().withName("Grilled Sandwich")
                .withTime("10")
                .withGrains("50g, Bread")
                .withOthers("50g, Cheese", "100g, Oil")
                .withSteps("Spread butter on bread", "Heat pan to medium heat")
                .withGoals("Wholesome Wholemeal").build();
        expectedModel.setRecipe(recipeToAddIngredients, expectedRecipe);

        assertCommandSuccess(addIngredientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);

        Index outOfBoundIndex = INDEX_THIRD_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        AddIngredientCommand addIngredientCommand = new AddIngredientCommand(
                outOfBoundIndex, new EditRecipeDescriptor());

        assertCommandFailure(addIngredientCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditRecipeDescriptor firstEditRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> newOtherIngredients = new TreeSet<>();
        newOtherIngredients.add(otherIngredientToAdd);
        firstEditRecipeDescriptor.setOthers(newOtherIngredients);

        EditRecipeDescriptor secondEditRecipeDescriptor = new EditRecipeDescriptor();
        Set<Protein> newProteinIngredients = new TreeSet<>();
        newProteinIngredients.add(proteinIngredientToAdd);
        secondEditRecipeDescriptor.setProteins(newProteinIngredients);

        // Base command for comparison
        AddIngredientCommand addIngredientFirstCommand = new AddIngredientCommand(
                INDEX_SECOND_RECIPE, firstEditRecipeDescriptor);
        // Different recipe, same EditRecipeDescriptor
        AddIngredientCommand addIngredientSecondCommand = new AddIngredientCommand(
                INDEX_THIRD_RECIPE, firstEditRecipeDescriptor);
        // Same recipe, different EditRecipeDescriptor
        AddIngredientCommand addIngredientThirdCommand = new AddIngredientCommand(
                INDEX_SECOND_RECIPE, secondEditRecipeDescriptor);

        // same object -> returns true
        assertTrue(addIngredientFirstCommand.equals(addIngredientFirstCommand));

        // same values -> returns true
        AddIngredientCommand addIngredientFirstCommandCopy = new AddIngredientCommand(
                INDEX_SECOND_RECIPE, firstEditRecipeDescriptor);
        assertTrue(addIngredientFirstCommand.equals(addIngredientFirstCommandCopy));

        // different types -> returns false
        assertFalse(addIngredientFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addIngredientFirstCommand.equals(null));

        // different recipe, same EditRecipeDescriptor -> returns false
        assertFalse(addIngredientFirstCommand.equals(addIngredientSecondCommand));

        // same recipe, different EditRecipeDescriptor -> returns false
        assertFalse(addIngredientFirstCommand.equals(addIngredientThirdCommand));
    }
}

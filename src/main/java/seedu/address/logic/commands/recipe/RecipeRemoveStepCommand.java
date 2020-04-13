package seedu.address.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.step.Step;
import seedu.address.model.step.UniqueStepList;

/**
 * Removes a step from a recipe.
 */
public class RecipeRemoveStepCommand extends RecipeRemoveCommand {

    public static final String MESSAGE_SUCCESS = "Step deleted for %1$s: %2$s";

    private final Index recipeIndex;
    private final Index stepIndex;

    public RecipeRemoveStepCommand(Index recipeIndex, Index stepIndex) {
        requireNonNull(recipeIndex);
        requireNonNull(stepIndex);
        this.recipeIndex = recipeIndex;
        this.stepIndex = stepIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredCookbookRecipeList();

        if (recipeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    MESSAGE_USAGE));
        }

        Recipe recipeToEdit = lastShownList.get(recipeIndex.getZeroBased());
        UniqueStepList targetStepList = recipeToEdit.getSteps();

        if (stepIndex.getZeroBased() >= targetStepList.asUnmodifiableObservableList().size()) {
            // ensure the step index is valid
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    MESSAGE_USAGE));
        }

        assert (stepIndex.getZeroBased() < targetStepList.asUnmodifiableObservableList().size());
        Step toRemove = targetStepList.remove(stepIndex);

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setSteps(targetStepList);
        Recipe editedRecipe = EditRecipeDescriptor.createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setCookbookRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredCookbookRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedRecipe.getName().fullRecipeName, toRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeRemoveStepCommand // instanceof handles nulls
                && stepIndex.equals(((RecipeRemoveStepCommand) other).stepIndex));
    }
}

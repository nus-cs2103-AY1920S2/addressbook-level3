package seedu.address.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STEP_DISPLAYED_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.step.Step;
import seedu.address.model.step.UniqueStepList;

/**
 * Adds a step to a recipe.
 */
public class RecipeAddStepCommand extends RecipeAddCommand {

    public static final String MESSAGE_SUCCESS = "New step added for %1$s: %2$s";
    public static final String NEXT_KEYWORD = "next";

    private final Index recipeIndex;
    private final Optional<Index> stepIndex;
    private final Step toAdd;

    public RecipeAddStepCommand(Index recipeIndex, Optional<Index> stepIndex, Step toAdd) {
        requireNonNull(recipeIndex);
        requireNonNull(toAdd);

        this.recipeIndex = recipeIndex;
        this.stepIndex = stepIndex;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredCookbookRecipeList();

        if (recipeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    RecipeAddCommand.MESSAGE_USAGE));
        }

        Recipe recipeToEdit = lastShownList.get(recipeIndex.getZeroBased());
        UniqueStepList targetStepList = recipeToEdit.getSteps();

        if (targetStepList.contains(toAdd)) {
            throw new CommandException(((Messages.MESSAGE_DUPLICATE_STEPS)));
        }

        if (stepIndex.isPresent()) {
            if (stepIndex.get().getZeroBased() > targetStepList.asUnmodifiableObservableList().size()) {
                // ensure the step index is valid
                throw new CommandException(String.format(MESSAGE_INVALID_STEP_DISPLAYED_INDEX,
                        RecipeAddCommand.MESSAGE_USAGE));
            }

            targetStepList.add(stepIndex.get(), toAdd);
        } else {
            targetStepList.add(toAdd);
        }

        assert stepIndex.isPresent();
        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setSteps(targetStepList);
        Recipe editedRecipe = EditRecipeDescriptor.createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setCookbookRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredCookbookRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedRecipe.getName().fullRecipeName, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeAddStepCommand // instanceof handles nulls
                && toAdd.equals(((RecipeAddStepCommand) other).toAdd)
                && stepIndex.equals(((RecipeAddStepCommand) other).stepIndex));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.createEditedRecipe;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;

/**
 * Deletes step(s) from an existing recipe in the recipe book.
 */
public class DeleteStepCommand extends Command {

    public static final String COMMAND_WORD = "deleteStep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes step(s) from an existing recipe in the "
            + "recipe book.\n"
            + "Parameters: [INDEX of recipe] [Step numbers]\n"
            + "Example: " + COMMAND_WORD + " 1 3 5 (deletes steps 3 and 5 of recipe 1)";

    public static final String MESSAGE_ADD_STEPS_SUCCESS = "Successfully added step(s) to %1$s!";

    private final Index index;
    private final int[] stepNumbers;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param stepNumbers is the array of step numbers that the user wishes to delete from the recipe
     */
    public DeleteStepCommand(Index index, int... stepNumbers) {
        this.index = index;
        this.stepNumbers = stepNumbers;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        EditCommand.EditRecipeDescriptor editRecipeDescriptor = new EditCommand.EditRecipeDescriptor();
        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());

        List<Step> updatedStepsList = new ArrayList<>(recipeToEdit.getSteps());
        for (int i = 0; i < stepNumbers.length; i++) {
            updatedStepsList.remove(stepNumbers[i] - i);
        }
        editRecipeDescriptor.setSteps(updatedStepsList);

        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.commitRecipeBook();

        return new CommandResult(String.format(MESSAGE_ADD_STEPS_SUCCESS, recipeToEdit.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteStepCommand // instanceof handles nulls
                && index.equals(((DeleteStepCommand) other).index)
                && Arrays.equals(stepNumbers, ((DeleteStepCommand) other).stepNumbers)); // state check
    }
}

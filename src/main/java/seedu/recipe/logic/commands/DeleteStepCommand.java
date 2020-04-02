package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.commands.EditCommand.createEditedRecipe;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Step;

/**
 * Deletes step(s) from an existing recipe in the recipe book.
 */
public class DeleteStepCommand extends Command {

    public static final String COMMAND_WORD = "deletestep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes step(s) from an existing recipe in the "
            + "recipe book.\n"
            + "Parameters: [INDEX of recipe] [Step numbers]\n"
            + "Example: " + COMMAND_WORD + " 1 3 5 (deletes steps 3 and 5 of recipe 1)";

    public static final String MESSAGE_DELETE_STEPS_SUCCESS = "Successfully deleted step(s) from %1$s!";
    public static final String MESSAGE_INVALID_STEP_INDEX = "Attempting to delete a non-existent step";

    private final Index index;
    private final Integer[] stepNumbers;
    private final CommandType commandType;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param stepNumbers is the array of step numbers that the user wishes to delete from the recipe
     */
    public DeleteStepCommand(Index index, Integer[] stepNumbers) {
        this.index = index;
        this.stepNumbers = stepNumbers;
        this.commandType = CommandType.MAIN;
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
        if (stepNumbers[stepNumbers.length - 1] > updatedStepsList.size() - 1) {
            throw new CommandException(MESSAGE_INVALID_STEP_INDEX);
        }

        for (int i = 0; i < stepNumbers.length; i++) {
            updatedStepsList.remove(stepNumbers[i] - i);
        }
        editRecipeDescriptor.setSteps(updatedStepsList);

        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);
        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);

        return new CommandResult(String.format(MESSAGE_DELETE_STEPS_SUCCESS, recipeToEdit.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteStepCommand // instanceof handles nulls
                && index.equals(((DeleteStepCommand) other).index)
                && Arrays.equals(stepNumbers, ((DeleteStepCommand) other).stepNumbers)); // state check
    }
}

package cookbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import cookbuddy.commons.core.Messages;
import cookbuddy.commons.core.index.Index;
import cookbuddy.logic.commands.exceptions.CommandException;
import cookbuddy.model.Model;
import cookbuddy.model.recipe.Recipe;

/**
 * Deletes a recipe identified using it's displayed index from the recipe book.
 */
public class DuplicateCommand extends Command {

    public static final String COMMAND_WORD = "duplicate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": A duplicate of the recipe identified by the index number is added to the recipe list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RECIPE_SUCCESS = "Recipe duplicated: %1$s";

    private final Index targetIndex;

    public DuplicateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToDuplicate = lastShownList.get(targetIndex.getZeroBased());
        model.duplicateRecipe(recipeToDuplicate);
        return new CommandResult(String.format(MESSAGE_DELETE_RECIPE_SUCCESS, recipeToDuplicate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DuplicateCommand // instanceof handles nulls
                && targetIndex.equals(((DuplicateCommand) other).targetIndex)); // state check
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;

/**
 * Deletes a recipe identified using it's displayed index from the recipe book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recipe identified by the index number(s) used in the displayed recipe list.\n"
            + "Parameters: INDEX NUMBER(s) (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index[] targetIndex;

    public DeleteCommand(Index[] targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();
        StringBuilder sb = new StringBuilder().append("Deleted ");

        for (int i = 0; i < targetIndex.length; i++) {
            Index indexAfterEachDeletion = Index.fromZeroBased(targetIndex[i].getZeroBased() - i);
            if (indexAfterEachDeletion.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
            }

            Recipe recipeToDelete = lastShownList.get(indexAfterEachDeletion.getZeroBased());
            model.deleteRecipe(recipeToDelete);
            if (i == targetIndex.length - 1 && targetIndex.length != 1) {
                sb.append(" and ");
            }
            sb.append(recipeToDelete.getName().toString());
            if (i < targetIndex.length - 2) {
                sb.append(", ");
            }
        }
        sb.append(" from recipe book!");
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && Arrays.equals(targetIndex, ((DeleteCommand) other).targetIndex)); // state check
    }
}

package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Deletes a recipe identified using it's displayed index from the recipe book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recipe identified by the index number(s) used in the displayed recipe list.\n"
            + "Parameters: [recipe index] <recipe index>... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 3 4 (deletes recipe 1, 3, and 4 from the recipe book)";

    public static final String MESSAGE_SUCCESS = "Deleted %1$s from recipe book!";

    private final Tab recipesTab = Tab.RECIPES;
    private final Index[] targetIndexes;
    private final CommandType commandType;

    public DeleteCommand(Index[] targetIndexes) {
        this.targetIndexes = targetIndexes;
        this.commandType = CommandType.MAIN;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();
        List<String> deletedRecipesList = new ArrayList<>();

        if (!canDeleteTargetRecipes(lastShownList.size(), targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        for (int i = 0; i < targetIndexes.length; i++) {
            Index indexAfterEachDeletion = Index.fromZeroBased(targetIndexes[i].getZeroBased() - i);
            Recipe recipeToDelete = lastShownList.get(indexAfterEachDeletion.getZeroBased());
            model.deleteRecipe(recipeToDelete);
            deletedRecipesList.add(recipeToDelete.getName().toString());
        }

        model.commitBook(commandType, recipesTab);
        String finalMessage = String.format(MESSAGE_SUCCESS, getListAsFormattedString(deletedRecipesList));
        return new CommandResult(finalMessage, false, false, recipesTab, false);
    }

    /**
     * Formats a list of recipe names into a string with appropriate commas and conjunctions.
     */
    private String getListAsFormattedString(List<String> listToFormat) {
        StringBuilder formattedString = new StringBuilder();
        for (int i = 0; i < listToFormat.size(); i++) {
            if (i == listToFormat.size() - 1 && listToFormat.size() != 1) {
                formattedString.append(" and ");
            }
            formattedString.append(listToFormat.get(i));
            if (i < listToFormat.size() - 2) {
                formattedString.append(", ");
            }
        }
        return formattedString.toString();
    }

    /**
     * Checks if the recipe that the user wishes to remove exists within the recipe list.
     */
    private boolean canDeleteTargetRecipes(int lastShownListSize, Index[] targetIndexes) {
        for (int i = targetIndexes.length - 1; i >= 0; i--) {
            if (targetIndexes[i].getOneBased() > lastShownListSize) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && Arrays.equals(targetIndexes, ((DeleteCommand) other).targetIndexes)); // state check
    }
}

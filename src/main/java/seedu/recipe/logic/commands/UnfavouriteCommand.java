package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.commands.EditCommand.createEditedRecipe;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Unfavourites a recipe identified using it's displayed index from the recipe book.
 */
public class UnfavouriteCommand extends Command {
    public static final String COMMAND_WORD = "unfavourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavourites the recipe identified by the index number(s) used in the displayed recipe list.\n"
            + "Parameters: INDEX NUMBER(s) (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Removed %1$s from favourites!";
    public static final String MESSAGE_ALREADY_NOT_FAVOURITE = "%1$s already not in favourites!";

    private final Tab recipesTab = Tab.RECIPES;
    private final Index[] targetIndex;
    private final CommandType commandType;

    public UnfavouriteCommand(Index[] targetIndex) {
        this.targetIndex = targetIndex;
        this.commandType = CommandType.MAIN;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();
        List<String> successfullyUnfavouritedRecipes = new ArrayList<>();
        List<String> alreadyUnfavouritedRecipes = new ArrayList<>();

        if (!canUnfavouriteTargetRecipes(lastShownList.size(), targetIndex)) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        for (Index index : targetIndex) {
            Recipe recipeToUnfavourite = lastShownList.get(index.getZeroBased());
            if (recipeToUnfavourite.isFavourite()) {
                EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
                editRecipeDescriptor.setFavourite(false);
                Recipe editedRecipe = createEditedRecipe(recipeToUnfavourite, editRecipeDescriptor);
                model.setRecipe(recipeToUnfavourite, editedRecipe);
                successfullyUnfavouritedRecipes.add(recipeToUnfavourite.getName().toString());
            } else {
                alreadyUnfavouritedRecipes.add(recipeToUnfavourite.getName().toString());
            }
        }

        StringBuilder sb = new StringBuilder();
        if (!successfullyUnfavouritedRecipes.isEmpty()) {
            sb.append(String.format(MESSAGE_SUCCESS, getListAsFormattedString(successfullyUnfavouritedRecipes)));
            sb.append("\n");
        }
        if (!alreadyUnfavouritedRecipes.isEmpty()) {
            sb.append(
                    String.format(MESSAGE_ALREADY_NOT_FAVOURITE, getListAsFormattedString(alreadyUnfavouritedRecipes)));
        }

        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);
        return new CommandResult(sb.toString(), false, recipesTab, false);
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
     * Checks if the recipe that the user wishes to unfavourite exists within the recipe list.
     */
    private boolean canUnfavouriteTargetRecipes(int lastShownListSize, Index[] targetIndex) {
        for (int i = targetIndex.length - 1; i >= 0; i--) {
            if (targetIndex[i].getOneBased() > lastShownListSize) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles nulls
                && Arrays.equals(targetIndex, ((UnfavouriteCommand) other).targetIndex)); // state check
    }
}

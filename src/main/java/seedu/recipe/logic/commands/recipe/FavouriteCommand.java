package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.commands.recipe.EditCommand.createEditedRecipe;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.commands.recipe.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Favourites a recipe identified using it's displayed index from the recipe book.
 */
public class FavouriteCommand extends Command {
    public static final String COMMAND_WORD = "favourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the recipe identified by the index number(s) used in the displayed recipe list.\n"
            + "Parameters: [recipe index] <recipe index>... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 3 4 (favourites recipes 1, 3, and 4)";

    public static final String MESSAGE_SUCCESS = "Added %1$s to favourites!";
    public static final String MESSAGE_ALREADY_FAVOURITE = "%1$s already in favourites!";

    private final Tab recipesTab = Tab.RECIPES;
    private final Index[] targetIndexes;
    private final CommandType commandType;

    public FavouriteCommand(Index[] targetIndexes) {
        this.targetIndexes = targetIndexes;
        this.commandType = CommandType.MAIN;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();
        List<String> successfullyFavouritedRecipes = new ArrayList<>();
        List<String> alreadyFavouritedRecipes = new ArrayList<>();

        if (!canFavouriteTargetRecipes(lastShownList.size(), targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        for (Index index : targetIndexes) {
            Recipe recipeToFavourite = lastShownList.get(index.getZeroBased());
            if (!recipeToFavourite.isFavourite()) {
                EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
                editRecipeDescriptor.setFavourite(true);
                Recipe editedRecipe = createEditedRecipe(recipeToFavourite, editRecipeDescriptor);
                model.setRecipe(recipeToFavourite, editedRecipe);
                successfullyFavouritedRecipes.add(recipeToFavourite.getName().toString());
            } else {
                alreadyFavouritedRecipes.add(recipeToFavourite.getName().toString());
            }
        }

        StringBuilder sb = new StringBuilder();
        if (!successfullyFavouritedRecipes.isEmpty()) {
            sb.append(String.format(MESSAGE_SUCCESS, getListAsFormattedString(successfullyFavouritedRecipes)));
            sb.append("\n");
        }
        if (!alreadyFavouritedRecipes.isEmpty()) {
            sb.append(String.format(MESSAGE_ALREADY_FAVOURITE, getListAsFormattedString(alreadyFavouritedRecipes)));
        }

        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType, recipesTab);
        return new CommandResult(sb.toString(), false, false, recipesTab, false);
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
     * Checks if the recipe that the user wishes to favourite exists within the recipe list.
     */
    private boolean canFavouriteTargetRecipes(int lastShownListSize, Index[] targetIndexes) {
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
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && Arrays.equals(targetIndexes, ((FavouriteCommand) other).targetIndexes)); // state check
    }
}

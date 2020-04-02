package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.Arrays;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;

/**
 * Unfavourites a recipe identified using it's displayed index from the recipe book.
 */
public class UnfavouriteCommand extends Command {
    public static final String COMMAND_WORD = "unfavourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavourites the recipe identified by the index number(s) used in the displayed recipe list.\n"
            + "Parameters: INDEX NUMBER(s) (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

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
        StringBuilder sb = new StringBuilder().append("Removed ");

        for (int i = 0; i < targetIndex.length; i++) {
            if (targetIndex[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
            }

            Recipe recipeToUnfavourite = lastShownList.get(targetIndex[i].getZeroBased());
            model.unfavouriteRecipe(recipeToUnfavourite);
            if (i == targetIndex.length - 1 && targetIndex.length != 1) {
                sb.append(" and ");
            }
            sb.append(recipeToUnfavourite.getName().toString());
            if (i < targetIndex.length - 2) {
                sb.append(", ");
            }
        }
        sb.append(" from favourites!");
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles nulls
                && Arrays.equals(targetIndex, ((UnfavouriteCommand) other).targetIndex)); // state check
    }
}

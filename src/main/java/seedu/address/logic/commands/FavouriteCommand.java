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
 * Favourites a recipe identified using it's displayed index from the address book.
 */
public class FavouriteCommand extends Command {
    public static final String COMMAND_WORD = "favourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the recipe identified by the index number(s) used in the displayed recipe list.\n"
            + "Parameters: INDEX NUMBER(s) (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index[] targetIndex;

    public FavouriteCommand(Index[] targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();
        StringBuilder sb = new StringBuilder().append("Added ");

        for (int i = 0; i < targetIndex.length; i++) {
            if (targetIndex[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
            }

            Recipe recipeToFavourite = lastShownList.get(targetIndex[i].getZeroBased());
            model.favouriteRecipe(recipeToFavourite);
            if (i == targetIndex.length - 1 && targetIndex.length != 1) {
                sb.append(" and ");
            }
            sb.append(recipeToFavourite.getName().toString());
            if (i < targetIndex.length - 2) {
                sb.append(", ");
            }
        }
        sb.append(" to favourites!");
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && Arrays.equals(targetIndex, ((FavouriteCommand) other).targetIndex)); // state check
    }
}

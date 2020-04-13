package seedu.address.logic.commands.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;

/**
 * Removes a recipe identified using it's displayed index from the cookbook.
 */
public class CookbookRemoveCommand extends CookbookCommand {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": remove the recipe of the specified index from the cookbook\n\n"
            + "Parameters: INDEX (must be a positive valid integer)\n"
            + "Example: "
            + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + RECIPE_KEYWORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted recipe: %1$s from the cookbook";

    private final Index targetIndex;

    public CookbookRemoveCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Recipe> lastShownList = model.getFilteredCookbookRecipeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    CookbookRemoveCommand.MESSAGE_USAGE));
        }

        Recipe recipeToRemove = lastShownList.get(targetIndex.getZeroBased());
        model.removeCookbookRecipe(recipeToRemove);
        return new CommandResult(String.format(MESSAGE_SUCCESS, recipeToRemove.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookbookRemoveCommand // instanceof handles nulls
                && targetIndex.equals(((CookbookRemoveCommand) other).targetIndex)); // state check
    }
}

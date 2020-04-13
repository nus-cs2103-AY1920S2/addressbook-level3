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
 * View a recipe identified using it's displayed index from the cookbook.
 */
public class CookbookViewCommand extends CookbookCommand {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": views the recipe identified by the index number used in the displayed recipe list.\n\n"
            + "Parameters: INDEX (must be a positive valid integer)\n"
            + "Example: " + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + RECIPE_KEYWORD + " 1";

    public static final String MESSAGE_SUCCESS = "Viewing recipe %1$d (%2$s)";

    private final Index targetIndex;

    public CookbookViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Recipe> lastShownList = model.getFilteredCookbookRecipeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    CookbookViewCommand.MESSAGE_USAGE));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased(),
                lastShownList.get(targetIndex.getZeroBased()).getName().fullRecipeName),
                true, targetIndex);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookbookViewCommand // instanceof handles nulls
                && targetIndex.equals(((CookbookViewCommand) other).targetIndex)); // state check
    }
}

package seedu.address.logic.commands.cookbook;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.RecipeDefaultComparator;

/**
 * Shows all the recipes in the cookbook.
 */
public class CookbookListCommand extends CookbookCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " " + COMMAND_WORD
            + ": shows the list of recipes in the cookbook.\n"
            + "Example: " + COMMAND_CATEGORY + " " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Showing all recipes in the cookbook.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortCookbook(new RecipeDefaultComparator());
        model.updateFilteredCookbookRecipeList(Model.PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof CookbookListCommand;
    }
}

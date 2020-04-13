package seedu.address.logic.commands.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.recipe.RecipeContainsTagsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CookbookSearchByTagCommand extends CookbookSearchCommand {

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " " + SEARCH_TAG_COMMAND
            + ": Finds all recipes whose recipe names contain any of the specified tags (case-insensitive) and"
            + " displays them as an alphabetically sorted list with index numbers.\n"
            + "Parameters: " + PREFIX_TAG + "TAG\n\n"
            + "Example: " + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + SEARCH_TAG_COMMAND + " "
            + PREFIX_TAG + "breakfast";

    private final RecipeContainsTagsPredicate predicate;

    public CookbookSearchByTagCommand(RecipeContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredCookbookRecipeList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RECIPES_LISTED_OVERVIEW, model.getFilteredCookbookRecipeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookbookSearchByTagCommand // instanceof handles nulls
                && (predicate.equals(((CookbookSearchByTagCommand) other).predicate)));
    }
}


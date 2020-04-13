package seedu.address.logic.commands.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_RECIPES_LISTED_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_KEYWORD;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CookbookSearchByKeywordCommand extends CookbookSearchCommand {

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": finds all recipes whose recipe names contain any of "
            + "the specified keywords (case-insensitive) and displays them as an alphabetically sorted list with "
            + "index numbers.\n"
            + "Parameters: " + PREFIX_SEARCH_KEYWORD + "KEYWORD\n"
            + "Example:" + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " " + RECIPE_KEYWORD + " " + PREFIX_SEARCH_KEYWORD + "bacon";

    private final RecipeNameContainsKeywordsPredicate predicate;

    public CookbookSearchByKeywordCommand(RecipeNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredCookbookRecipeList(predicate);
        return new CommandResult(
                String.format(MESSAGE_RECIPES_LISTED_OVERVIEW, model.getFilteredCookbookRecipeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookbookSearchByKeywordCommand
                && predicate.equals(((CookbookSearchByKeywordCommand) other).predicate));
    }
}


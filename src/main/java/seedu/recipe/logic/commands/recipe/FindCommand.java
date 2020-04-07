package seedu.recipe.logic.commands.recipe;

import static java.util.Objects.requireNonNull;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.NameContainsKeywordsPredicate;
import seedu.recipe.ui.tab.Tab;

/**
 * Finds and lists all recipes in recipe book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all recipes whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Thai beef bowl";

    private final NameContainsKeywordsPredicate predicate;
    private final Tab recipesTab = Tab.RECIPES;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecipeList(predicate);
        String message = String.format(Messages.MESSAGE_RECIPES_LISTED_OVERVIEW, model.getFilteredRecipeList().size());
        return new CommandResult(message, false, false, recipesTab, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

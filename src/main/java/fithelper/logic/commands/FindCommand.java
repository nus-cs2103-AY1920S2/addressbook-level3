package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.HOME;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.Messages;
import fithelper.model.Model;
import fithelper.model.entry.NameContainsKeywordsPredicate;

/**
 * Finds and lists all entries in fitHelper whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entries whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "apple banana";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEntryList(predicate);
        String feedback = String.format(Messages.MESSAGE_FOOD_LISTED_OVERVIEW, model.getFilteredFoodEntryList().size())
                + " "
                + String.format(Messages.MESSAGE_SPORTS_LISTED_OVERVIEW, model.getFilteredSportsEntryList().size());
        return new CommandResult(feedback, HOME, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

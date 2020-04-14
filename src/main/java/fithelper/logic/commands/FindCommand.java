package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_KEYWORD;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.model.Model.PREDICATE_SHOW_NO_ENTRIES;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.Messages;
import fithelper.model.Model;
import fithelper.model.entry.NameContainsKeywordsPredicate;
import fithelper.model.entry.Type;

/**
 * Finds and lists all entries in fitHelper whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all entries whose names contain any of "
            + "the specified keywords.\n"
            + "Parameters: ["
            + PREFIX_TYPE + "TYPE] ["
            + PREFIX_KEYWORD + "keyword list]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "sports "
            + PREFIX_KEYWORD + "noodles ";

    private final NameContainsKeywordsPredicate predicate;
    private final Type findType;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.findType = null;
        this.predicate = predicate;
    }

    public FindCommand(Type findType, NameContainsKeywordsPredicate predicate) {
        this.findType = findType;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String feedback = "";
        model.updateFilteredReminderEntryList(predicate);
        if (this.findType == null) {
            model.updateFilteredEntryList(predicate);
            feedback = String.format(Messages.MESSAGE_FOOD_LISTED_OVERVIEW, model.getFilteredFoodEntryList().size())
                    + " "
                    + String.format(Messages.MESSAGE_SPORTS_LISTED_OVERVIEW, model.getFilteredSportsEntryList().size());
        } else {
            if (this.findType.getValue().equalsIgnoreCase("food")) {
                model.updateFilteredFoodEntryList(predicate);
                model.updateFilteredSportEntryList(PREDICATE_SHOW_NO_ENTRIES);
                feedback = String.format(Messages.MESSAGE_FOOD_LISTED_OVERVIEW,
                        model.getFilteredFoodEntryList().size());
            } else {
                model.updateFilteredSportEntryList(predicate);
                model.updateFilteredFoodEntryList(PREDICATE_SHOW_NO_ENTRIES);
                feedback = String.format(Messages.MESSAGE_SPORTS_LISTED_OVERVIEW,
                        model.getFilteredSportsEntryList().size());
            }
        }
        return new CommandResult(feedback, CommandResult.DisplayedPage.HOME, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

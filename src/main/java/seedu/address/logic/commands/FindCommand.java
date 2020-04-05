package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.GroupContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "(ab)find";
    public static final String COMMAND_FUNCTION = "Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + COMMAND_WORD + " [-g/GROUPNAME] [-n/WORD] [-t/TAG]\n"
            + "Example: " + COMMAND_WORD + " -g/NUS -n/Lim";

    private final GroupContainsKeywordsPredicate groupPredicate;
    private final NameContainsKeywordsPredicate wordPredicate;
    private final TagsContainsKeywordsPredicate tagPredicate;

    public FindCommand(GroupContainsKeywordsPredicate groupPredicate,
                       NameContainsKeywordsPredicate wordPredicate,
                       TagsContainsKeywordsPredicate tagPredicate) {
        // we split the different keywords into different predicates
        this.groupPredicate = groupPredicate;
        this.wordPredicate = wordPredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (groupPredicate.size() == 0 && wordPredicate.size() == 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(tagPredicate); // 001
        } else if (groupPredicate.size() == 0 && wordPredicate.size() != 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(wordPredicate); // 010
        } else if (groupPredicate.size() == 0 && wordPredicate.size() != 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(wordPredicate.and(tagPredicate)); // 011
        } else if (groupPredicate.size() != 0 && wordPredicate.size() == 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(groupPredicate); // 100
        } else if (groupPredicate.size() != 0 && wordPredicate.size() == 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(groupPredicate.and(tagPredicate)); // 101
        } else if (groupPredicate.size() != 0 && wordPredicate.size() != 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(groupPredicate.and(wordPredicate)); // 110
        } else if (groupPredicate.size() != 0 && wordPredicate.size() != 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(groupPredicate.and(wordPredicate).and(tagPredicate));
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && groupPredicate.equals(((FindCommand) other).groupPredicate)
                && wordPredicate.equals(((FindCommand) other).wordPredicate)
                && tagPredicate.equals(((FindCommand) other).tagPredicate)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.OrganizationContainsKeywordsPredicate;
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
            + "Parameters: " + COMMAND_WORD + " [o/ORGANIZATION] [n/WORD] [t/TAG]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ORGANIZATION + "NUS "
            + PREFIX_NAME + " Lim";

    private final OrganizationContainsKeywordsPredicate organizationPredicate;
    private final NameContainsKeywordsPredicate wordPredicate;
    private final TagsContainsKeywordsPredicate tagPredicate;

    public FindCommand(OrganizationContainsKeywordsPredicate organizationPredicate,
                       NameContainsKeywordsPredicate wordPredicate,
                       TagsContainsKeywordsPredicate tagPredicate) {
        // we split the different keywords into different predicates
        this.organizationPredicate = organizationPredicate;
        this.wordPredicate = wordPredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (organizationPredicate.size() == 0 && wordPredicate.size() == 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(tagPredicate); // 001
        } else if (organizationPredicate.size() == 0 && wordPredicate.size() != 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(wordPredicate); // 010
        } else if (organizationPredicate.size() == 0 && wordPredicate.size() != 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(wordPredicate.and(tagPredicate)); // 011
        } else if (organizationPredicate.size() != 0 && wordPredicate.size() == 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(organizationPredicate); // 100
        } else if (organizationPredicate.size() != 0 && wordPredicate.size() == 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(organizationPredicate.and(tagPredicate)); // 101
        } else if (organizationPredicate.size() != 0 && wordPredicate.size() != 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(organizationPredicate.and(wordPredicate)); // 110
        } else if (organizationPredicate.size() != 0 && wordPredicate.size() != 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(organizationPredicate.and(wordPredicate).and(tagPredicate));
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && organizationPredicate.equals(((FindCommand) other).organizationPredicate)
                && wordPredicate.equals(((FindCommand) other).wordPredicate)
                && tagPredicate.equals(((FindCommand) other).tagPredicate)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}

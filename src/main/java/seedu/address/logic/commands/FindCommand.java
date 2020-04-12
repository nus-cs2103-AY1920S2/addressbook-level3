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
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers."
            + " At least one optional filed needs to be specified\n"
            + "Parameters: " + COMMAND_WORD + " [o/ORGANIZATION] [n/NAME] [t/TAG]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ORGANIZATION + "NUS "
            + PREFIX_NAME + " Lim";

    private final OrganizationContainsKeywordsPredicate organizationPredicate;
    private final NameContainsKeywordsPredicate namePredicate;
    private final TagsContainsKeywordsPredicate tagPredicate;

    public FindCommand(OrganizationContainsKeywordsPredicate organizationPredicate,
                       NameContainsKeywordsPredicate namePredicate,
                       TagsContainsKeywordsPredicate tagPredicate) {
        // we split the different keywords into different predicates
        this.organizationPredicate = organizationPredicate;
        this.namePredicate = namePredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        int osize = organizationPredicate.size();
        int nsize = namePredicate.size();
        int tsize = tagPredicate.size();

        if (osize == 0 && nsize == 0 && tsize != 0) {
            model.updateFilteredPersonList(tagPredicate); // 001
        } else if (osize == 0 && nsize != 0 && tsize == 0) {
            model.updateFilteredPersonList(namePredicate); // 010
        } else if (osize == 0 && nsize != 0 && tsize != 0) {
            model.updateFilteredPersonList(namePredicate.and(tagPredicate)); // 011
        } else if (osize != 0 && nsize == 0 && tsize == 0) {
            model.updateFilteredPersonList(organizationPredicate); // 100
        } else if (osize != 0 && nsize == 0 && tsize != 0) {
            model.updateFilteredPersonList(organizationPredicate.and(tagPredicate)); // 101
        } else if (osize != 0 && nsize != 0 && tsize == 0) {
            model.updateFilteredPersonList(organizationPredicate.and(namePredicate)); // 110
        } else if (osize != 0 && nsize != 0 && tsize != 0) {
            model.updateFilteredPersonList(organizationPredicate.and(namePredicate).and(tagPredicate));
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && organizationPredicate.equals(((FindCommand) other).organizationPredicate)
                && namePredicate.equals(((FindCommand) other).namePredicate)
                && tagPredicate.equals(((FindCommand) other).tagPredicate)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}

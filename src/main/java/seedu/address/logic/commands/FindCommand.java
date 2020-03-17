package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.GroupContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "(ab)find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + COMMAND_WORD + " [-g/GROUPNAME] [-n/WORD] [-t/TAG]\n"
            + "Example: " + COMMAND_WORD + " -g/NUS -n/Lim";

    public static final String FIND_SUCCESSFUL = "We found the following contacts: ";
    public static final String FIND_UNSUCCESSFUL = "No contacts were found with the keyword(s) specified.";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class); // for sarah's use
    private final GroupContainsKeywordsPredicate groupnamePredicate;
    private final NameContainsKeywordsPredicate wordPredicate;
    private final TagsContainsKeywordsPredicate tagPredicate;

    //private final NameContainsKeywordsPredicate predicate;

    public FindCommand(GroupContainsKeywordsPredicate groupnamePredicate, NameContainsKeywordsPredicate wordPredicate,
                       TagsContainsKeywordsPredicate tagPredicate) {
        // we split the different keywords into different predicates
        this.groupnamePredicate = groupnamePredicate;
        this.wordPredicate = wordPredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // when you do it with one person alone it works

        // check for emptiness. possible options are 000, 001, 010, 011, 100, 101, 110, 111
        // groupname, word, and tag IN THIS SPECIFIC ORDER
        if (groupnamePredicate.size() == 0 && wordPredicate.size() == 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(tagPredicate); // 001
        }
        else if (groupnamePredicate.size() == 0 && wordPredicate.size() != 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(wordPredicate); // 010
        }
        else if (groupnamePredicate.size() == 0 && wordPredicate.size() != 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(wordPredicate.and(tagPredicate)); // 011
        }
        else if (groupnamePredicate.size() != 0 && wordPredicate.size() == 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(groupnamePredicate); // 100
        }
        else if (groupnamePredicate.size() != 0 && wordPredicate.size() == 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(groupnamePredicate.and(tagPredicate)); // 101
        }
        else if (groupnamePredicate.size() != 0 && wordPredicate.size() != 0 && tagPredicate.size() == 0) {
            model.updateFilteredPersonList(groupnamePredicate.and(wordPredicate)); // 110
        }
        else if (groupnamePredicate.size() != 0 && wordPredicate.size() != 0 && tagPredicate.size() != 0) {
            model.updateFilteredPersonList(groupnamePredicate.and(wordPredicate).and(tagPredicate));
        }

        // we don't do anything for case 000

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /*
    // I don't know how to handle this now that I have split it into 3 predicates
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
     */

}

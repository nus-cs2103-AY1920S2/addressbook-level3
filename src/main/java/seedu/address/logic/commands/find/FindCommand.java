package seedu.address.logic.commands.find;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM;

import seedu.address.logic.commands.Command;
import seedu.address.model.item.field.NameContainsKeywordsPredicate;
import seedu.address.model.note.field.EntryTitleContainsKeywordsPredicate;

/**
 * Finds and lists all items in the storage whose name contains any of the argument keywords and whose type matches the
 * specified type.
 *
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... " + PREFIX_ITEM + " TYPE\n"
            + "Example: " + COMMAND_WORD + " Google " + PREFIX_ITEM + " int";

    protected final NameContainsKeywordsPredicate predicate;

    protected final EntryTitleContainsKeywordsPredicate notePredicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.notePredicate = null;
    }

    public FindCommand(EntryTitleContainsKeywordsPredicate notePredicate) {
        this.notePredicate = notePredicate;
        this.predicate = null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

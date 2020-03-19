package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

//import seedu.address.model.person.NameContainsKeywordsPredicate;

import nasa.commons.core.Messages;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;

/**
 * Finds and lists all activities in NASA whose name contains any of the argument keywords.
 * Represents the command for finding specific activities.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " assignment lab tutorial";

    //private final NameContainsKeywordsPredicate predicate;

    /*public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

     */

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult(Messages.MESSAGE_MODULE_LISTED_OVERVIEW);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand); // instanceof handles nulls
                //&& predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

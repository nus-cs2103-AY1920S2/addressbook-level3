package cardibuddy.logic.commands;

import static cardibuddy.commons.core.Messages.MESSAGE_TEST_ONGOING;
import static java.util.Objects.requireNonNull;

import cardibuddy.commons.core.Messages;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.FilterDeckKeywordsPredicate;


/**
 * Finds and lists all decks and cards in cardibuddy whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all decks whose titles contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [&] [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " java";

    private final FilterDeckKeywordsPredicate predicate;

    public FilterCommand(FilterDeckKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public FilterDeckKeywordsPredicate getPredicate() {
        return predicate;
    }

    @Override

    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        if (model.hasOngoingTestSession()) {
            throw new CommandException(MESSAGE_TEST_ONGOING);
        }
        model.updateFilteredDeckList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DECKS_LISTED_OVERVIEW, model.getFilteredDeckList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }


}

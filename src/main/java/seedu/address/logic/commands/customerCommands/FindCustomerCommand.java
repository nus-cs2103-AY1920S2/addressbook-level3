package seedu.address.logic.commands.customerCommands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.customer.AddressContainsKeywordsPredicate;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.EmailContainsKeywordsPredicate;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.customer.PhoneContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCustomerCommand extends Command {

    public static final String COMMAND_WORD = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "n/alice n/bob n/charlie";

    private final Predicate<Customer> predicate;

    public FindCustomerCommand(Predicate<Customer> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCustomerList(predicate);
        if (model.getFilteredCustomerList().size() == 0 && predicate instanceof NameContainsKeywordsPredicate) {
            return new CommandResult(String.format("No customer named %s found!", predicate.toString()));
        } else if (model.getFilteredCustomerList().size() == 0
                && predicate instanceof AddressContainsKeywordsPredicate) {
            return new CommandResult(String.format("No customer staying in the area %s found!", predicate.toString()));
        } else if (model.getFilteredCustomerList().size() == 0 && predicate instanceof EmailContainsKeywordsPredicate) {
            return new CommandResult(String.format("No customer with email %s found!", predicate.toString()));
        } else if (model.getFilteredCustomerList().size() == 0 && predicate instanceof PhoneContainsKeywordsPredicate) {
            return new CommandResult(String.format("No customer with phone number %s found!", predicate.toString()));
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredCustomerList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCustomerCommand // instanceof handles nulls
                && predicate.equals(((FindCustomerCommand) other).predicate)); // state check
    }
}

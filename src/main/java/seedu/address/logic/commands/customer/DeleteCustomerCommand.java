package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.transaction.Transaction;

/**
 * Deletes a customer identified using it's displayed index from the address book.
 */
public class DeleteCustomerCommand extends Command {

    public static final String COMMAND_WORD = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the customer identified by the index number used in the displayed customer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Customer: %1$s";

    private final Index targetIndex;

    public DeleteCustomerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // delete customer
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Customer customerToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(customerToDelete);

        // remove transactions with deleted customer
        updateTransactionList(model, customerToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, customerToDelete));
    }

    /**
     * Deletes transactions where the customer is the customer to be deleted.
     * @param model
     * @param customerToDelete
     */
    private void updateTransactionList(Model model, Customer customerToDelete) {
        List<Transaction> transactions = model.getInventorySystem().getTransactionList();

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getCustomer().equals(customerToDelete)) {
                model.deleteTransaction(transaction);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCustomerCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCustomerCommand) other).targetIndex)); // state check
    }
}

package seedu.expensela.testutil;

import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building editTransaction objects.
 */
public class EditTransactionDescriptorBuilder {

    private EditCommand.editTransaction descriptor;

    public EditTransactionDescriptorBuilder() {
        descriptor = new EditCommand.editTransaction();
    }

    public EditTransactionDescriptorBuilder(EditCommand.editTransaction descriptor) {
        this.descriptor = new EditCommand.editTransaction(descriptor);
    }

    /**
     * Returns an {@code editTransaction} with fields containing {@code transaction}'s details
     */
    public EditTransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new EditCommand.editTransaction();
        descriptor.setName(transaction.getName());
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDate(transaction.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code editTransaction} that we are building.
     */
    public EditTransactionDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code editTransaction} that we are building.
     */
    public EditTransactionDescriptorBuilder withPhone(String amount) {
        descriptor.setAmount(new Amount(amount, true));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code editTransaction} that we are building.
     */
    public EditTransactionDescriptorBuilder withAddress(String address) {
        descriptor.setDate(new Date(address));
        return this;
    }

    public EditCommand.editTransaction build() {
        return descriptor;
    }
}

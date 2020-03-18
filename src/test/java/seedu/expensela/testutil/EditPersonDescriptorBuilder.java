package seedu.expensela.testutil;

import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building editTransaction objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.editTransaction descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditCommand.editTransaction();
    }

    public EditPersonDescriptorBuilder(EditCommand.editTransaction descriptor) {
        this.descriptor = new EditCommand.editTransaction(descriptor);
    }

    /**
     * Returns an {@code editTransaction} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Transaction transaction) {
        descriptor = new EditCommand.editTransaction();
        descriptor.setName(transaction.getName());
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDate(transaction.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code editTransaction} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code editTransaction} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String amount) {
        descriptor.setAmount(new Amount(amount, true));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code editTransaction} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setDate(new Date(address));
        return this;
    }

    public EditCommand.editTransaction build() {
        return descriptor;
    }
}

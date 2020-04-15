package seedu.address.testutil.transaction;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.TransactionQuantity;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;

/**
 * A utility class to help with building EditTransactionDescriptor objects.
 */
public class EditTransactionDescriptorBuilder {

    private EditTransactionDescriptor descriptor;

    public EditTransactionDescriptorBuilder() {
        descriptor = new EditTransactionDescriptor();
    }

    public EditTransactionDescriptorBuilder(EditTransactionDescriptor descriptor) {
        this.descriptor = new EditTransactionDescriptor(descriptor);
    }

    /**
     * Sets the customer Index of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withCustomerIndex(Index index) {
        descriptor.setCustomerIndex(index);
        return this;
    }

    /**
     * Sets the product index of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withProductIndex(Index index) {
        descriptor.setProductIndex(index);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new TransactionQuantity(quantity));
        return this;
    }

    /**
     * Sets the {@code Money} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withMoney(String money) {
        descriptor.setMoney(new Money(money));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditTransactionDescriptor build() {
        return descriptor;
    }
}


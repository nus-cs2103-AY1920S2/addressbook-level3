package seedu.expensela.testutil;

import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Remark;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building editTransaction objects.
 */
public class EditTransactionDescriptorBuilder {

    private EditCommand.EditTransactionDescriptor descriptor;

    public EditTransactionDescriptorBuilder() {
        descriptor = new EditCommand.EditTransactionDescriptor();
    }

    public EditTransactionDescriptorBuilder(EditCommand.EditTransactionDescriptor descriptor) {
        this.descriptor = new EditCommand.EditTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTransactionDescriptor} with fields containing {@code transaction}'s details
     */
    public EditTransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new EditCommand.EditTransactionDescriptor();
        descriptor.setName(transaction.getName());
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDate(transaction.getDate());
        descriptor.setRemark(transaction.getRemark());
        descriptor.setCategory(transaction.getCategory());
    }

    /**
     * Sets the {@code Name} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount, false));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
        return this;
    }

    public EditCommand.EditTransactionDescriptor build() {
        return descriptor;
    }
}

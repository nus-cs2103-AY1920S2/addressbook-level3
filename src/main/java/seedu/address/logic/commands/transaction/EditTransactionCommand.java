package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.product.EditProductCommand.createEditedProduct;
import static seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import static seedu.address.logic.commands.product.EditProductCommand.MESSAGE_DUPLICATE_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANS_DESCIPTION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.product.EditProductCommand;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Money;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Description;
import seedu.address.model.util.Quantity;

/**
 * Edits the details of an existing transaction in the address book.
 */
public class EditTransactionCommand extends Command {

    public static final String COMMAND_WORD = "editt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER] "
            + "[" + PREFIX_PRODUCT + "PRODUCT] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "[" + PREFIX_TRANS_DESCIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "Bob "
            + PREFIX_PRODUCT + "WaterMelon "
            + PREFIX_DATETIME + "2020-02-20 10:00 "
            + PREFIX_QUANTITY + "30 "
            + PREFIX_MONEY + "30 "
            + PREFIX_TRANS_DESCIPTION + "under discount ";

    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited Transaction: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TRANSACTION =
            "This transaction already exists in the transaction list.";

    private final Index index;
    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * @param index of the transaction in the filtered transaction list to edit
     * @param editTransactionDescriptor details to edit the transaction with
     */
    public EditTransactionCommand(Index index, EditTransactionDescriptor editTransactionDescriptor) {
        requireNonNull(index);
        requireNonNull(editTransactionDescriptor);

        this.index = index;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        EditProductDescriptor editOriginalProductDescriptor = new EditProductDescriptor();
        EditProductDescriptor editUpdatedProductDescriptor = new EditProductDescriptor();

        List<Transaction> lastShownTransactionList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownTransactionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownTransactionList.get(index.getZeroBased());
        Transaction editedTransaction = createEditedTransaction(transactionToEdit, editTransactionDescriptor, model);

        if (!transactionToEdit.isSameTransaction(editedTransaction) && model.hasTransaction(editedTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        Product originalProductToEdit = model.findProductById(transactionToEdit.getProductId());
        Quantity originalProductOldQuantity = originalProductToEdit.getQuantity();
        Quantity originalProductNewQuantity = originalProductOldQuantity.plus(transactionToEdit.getQuantity());
        editOriginalProductDescriptor.setQuantity(originalProductNewQuantity);
        editOriginalProductDescriptor.setId(originalProductToEdit.getId());
        Product editedOriginalProduct = createEditedProduct(originalProductToEdit, editOriginalProductDescriptor);

        if (!originalProductToEdit.isSameProduct(editedOriginalProduct) && model.hasProduct(editedOriginalProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.setProduct(originalProductToEdit, editedOriginalProduct);

        Product updatedProductToEdit = model.findProductById(editedTransaction.getProductId());
        Quantity updatedProductOldQuantity = updatedProductToEdit.getQuantity();

        if (updatedProductOldQuantity.compareTo(editedTransaction.getQuantity()) >= 0) {
            Quantity updateProductNewQuantity = updatedProductOldQuantity.minus(editedTransaction.getQuantity());
            editUpdatedProductDescriptor.setQuantity(updateProductNewQuantity);
        } else {
            model.setProduct(editedOriginalProduct, originalProductToEdit);
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PRODUCT_AMOUNT,
                    updatedProductOldQuantity.value, updatedProductToEdit.getDescription().value));
        }

        Product editedUpdatedProduct = createEditedProduct(updatedProductToEdit, editUpdatedProductDescriptor);

        if (!updatedProductToEdit.isSameProduct(editedUpdatedProduct) && model.hasProduct(editedUpdatedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.setProduct(updatedProductToEdit, editedUpdatedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);


        model.setTransaction(transactionToEdit, editedTransaction);
        model.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_TRANSACTION_SUCCESS, editedTransaction));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Transaction createEditedTransaction(Transaction transactionToEdit,
                                                       EditTransactionDescriptor editTransactionDescriptor,
                                                       Model model) throws CommandException {
        assert transactionToEdit != null;

        List<Customer> lastShownCustomerList = model.getFilteredCustomerList();
        List<Product> lastShownProductList = model.getFilteredProductList();

        Customer updatedCustomer;
        if (editTransactionDescriptor.getCustomerIndex().isPresent()) {
            Index updatedCustomerIndex = editTransactionDescriptor.getCustomerIndex().get();

            if (updatedCustomerIndex.getZeroBased() >= lastShownCustomerList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            updatedCustomer = model.getFilteredCustomerList().get(updatedCustomerIndex.getZeroBased());
        } else {
            updatedCustomer = transactionToEdit.getCustomer();
        }

        Product updatedProduct;
        UUID updatedProductId;
        if (editTransactionDescriptor.getProductIndex().isPresent()) {
            Index updatedProductIndex = editTransactionDescriptor.getProductIndex().get();

            if (updatedProductIndex.getZeroBased() >= lastShownProductList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
            }

            updatedProduct = model.getFilteredProductList().get(updatedProductIndex.getZeroBased());
            updatedProductId = model.getFilteredProductList().get(updatedProductIndex.getZeroBased()).getId();
        } else {
            updatedProduct = transactionToEdit.getProduct();
            updatedProductId = transactionToEdit.getProductId();
        }

        DateTime updatedDateTime = editTransactionDescriptor.getDateTime().orElse(transactionToEdit.getDateTime());
        Quantity updatedQuantity = editTransactionDescriptor.getQuantity().orElse(transactionToEdit.getQuantity());
        Money updatedMoney = editTransactionDescriptor.getMoney().orElse(transactionToEdit.getMoney());
        Description updatedDescription = editTransactionDescriptor.getDescription()
                .orElse(transactionToEdit.getDescription());

        return new Transaction(updatedCustomer, updatedProduct, updatedProductId,
                updatedDateTime, updatedQuantity, updatedMoney, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTransactionCommand)) {
            return false;
        }

        // state check
        EditTransactionCommand e = (EditTransactionCommand) other;
        return index.equals(e.index)
                && editTransactionDescriptor.equals(e.editTransactionDescriptor);
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private Index customerIndex;
        private Index productIndex;
        private DateTime dateTime;
        private Quantity quantity;
        private Money money;
        private Description description;

        public EditTransactionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setCustomerIndex(toCopy.customerIndex);
            setProductIndex(toCopy.productIndex);
            setDateTime(toCopy.dateTime);
            setQuantity(toCopy.quantity);
            setMoney(toCopy.money);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(customerIndex, productIndex, dateTime, quantity,
                    money, description);
        }

        public void setCustomerIndex(Index customer) {
            this.customerIndex = customerIndex;
        }

        public void setProductIndex(Index product) {
            this.productIndex = productIndex;
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public void setMoney(Money money) {
            this.money = money;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Index> getCustomerIndex() {
            return Optional.ofNullable(customerIndex);
        }

        public Optional<Index> getProductIndex() {
            return Optional.ofNullable(productIndex);
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public Optional<Money> getMoney() {
            return Optional.ofNullable(money);
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            // state check
            EditTransactionDescriptor e = (EditTransactionDescriptor) other;

            return getCustomerIndex().equals(e.getCustomerIndex())
                    && getProductIndex().equals(e.getProductIndex())
                    && getDateTime().equals(e.getDateTime())
                    && getQuantity().equals(e.getQuantity())
                    && getMoney().equals(e.getMoney())
                    && getDescription().equals(e.getDescription());
        }
    }
}


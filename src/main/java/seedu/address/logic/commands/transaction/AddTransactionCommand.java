package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import static seedu.address.logic.commands.product.EditProductCommand.createEditedProduct;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANS_DESCRIPTION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionFactory;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.ui.NotificationWindow;

/**
 * Adds a transaction to the system.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to the application. \n"
            + "Parameters: "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_PRODUCT + "PRODUCT "
            + PREFIX_QUANTITY + "QUANTITY "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "[" + PREFIX_TRANS_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_PRODUCT + "1 "
            + PREFIX_QUANTITY + "1 "
            + PREFIX_DATETIME + "2020-02-20 10:00 "
            + PREFIX_MONEY + "30 "
            + PREFIX_TRANS_DESCRIPTION + "under discount ";

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "This transaction already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the address book";
    public static final String MESSAGE_MAXIMUM_SALES = "Maximum sales amount that can be stored is 1000000";

    private final TransactionFactory transactionFactory;
    private final EditProductDescriptor editProductDescriptor = new EditProductDescriptor();

    public AddTransactionCommand(TransactionFactory transactionFactory) {
        requireNonNull(transactionFactory);
        this.transactionFactory = transactionFactory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Transaction toAdd = transactionFactory.createTransaction(model);

        if (model.hasTransaction(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        updateProduct(model, toAdd);

        model.addTransaction(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Updates product details based on transaction to be added.
     * @param model
     * @param toAdd
     * @throws CommandException
     */
    private void updateProduct(Model model, Transaction toAdd) throws CommandException {
        List<Product> lastShownList = model.getFilteredProductList();

        Index productIndex = getProductIndex(lastShownList);

        Product productToEdit = lastShownList.get(productIndex.getZeroBased());
        Quantity newQuantity = getNewQuantity(toAdd, productToEdit);
        editProductDescriptor.setQuantity(newQuantity);

        Money newSales = getNewSales(toAdd, productToEdit);
        editProductDescriptor.setSales(newSales);
        editProductDescriptor.setThreshold(productToEdit.getThreshold());

        Product editedProduct = createEditedProduct(productToEdit, editProductDescriptor);
        if (!productToEdit.isSameProduct(editedProduct) && model.hasProduct(editedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.setProduct(productToEdit, editedProduct);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);

        int thresholdValue = Integer.parseInt(editedProduct.getThreshold().value);

        if (editedProduct.getQuantity().value <= thresholdValue) {
            NotificationWindow window = new NotificationWindow();
            window.show(editedProduct.getDescription(), editedProduct.getQuantity());
        }
    }

    private Index getProductIndex(List<Product> lastShownList) throws CommandException {
        Index productIndex = transactionFactory.getProductIndex();
        if (productIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }
        return productIndex;
    }

    private Quantity getNewQuantity(Transaction toAdd, Product productToEdit) throws CommandException {
        Quantity oldQuantity = productToEdit.getQuantity();

        if (oldQuantity.compareTo(toAdd.getQuantity()) < 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PRODUCT_AMOUNT,
                    oldQuantity.value, productToEdit.getDescription().value));
        }

        return oldQuantity.minus(toAdd.getQuantity());
    }

    private Money getNewSales(Transaction toAdd, Product productToEdit) throws CommandException {
        Money oldSales = productToEdit.getMoney();
        Money newSales;

        try {
            newSales = oldSales.plus(toAdd.getMoney());
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_MAXIMUM_SALES);
        }

        return newSales;
    }
}

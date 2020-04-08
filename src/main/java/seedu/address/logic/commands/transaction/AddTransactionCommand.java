package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PRODUCT;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_TRANSACTION;
import static seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import static seedu.address.logic.commands.product.EditProductCommand.createEditedProduct;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANS_DESCRIPTION;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.DateTime;
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
            + PREFIX_CUSTOMER + "CUSTOMER_ID "
            + PREFIX_PRODUCT + "PRODUCT_ID "
            + PREFIX_QUANTITY + "QUANTITY "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "[" + PREFIX_TRANS_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_PRODUCT + "1 "
            + PREFIX_QUANTITY + "1 "
            + PREFIX_DATETIME + DateTime.DEFAULT_VALUE.format(DateTime.DATE_TIME_FORMAT) + " "
            + PREFIX_MONEY + "30 "
            + PREFIX_TRANS_DESCRIPTION + "under discount ";

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";
    public static final String MESSAGE_ZERO_QUANTITY = "A transaction with 0 quantity is not allowed";

    private final TransactionFactory transactionFactory;

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

        model.addTransaction(toAdd);

        Product editedProduct = updateProduct(model, toAdd);

        if (editedProduct.isBelowThreshold()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd),
                    null,
                    editedProduct,
                    "",
                    false,
                    true,
                    false,
                    false);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Updates product details based on transaction to be added.
     * @param model
     * @param toAdd
     * @throws CommandException
     */
    private Product updateProduct(Model model, Transaction toAdd) throws CommandException {
        List<Product> lastShownList = model.getFilteredProductList();
        EditProductDescriptor editProductDescriptor = new EditProductDescriptor();

        Index productIndex = getProductIndex(lastShownList);

        Product productToEdit = lastShownList.get(productIndex.getZeroBased());
        editProductDescriptor.setQuantity(getNewQuantity(toAdd, productToEdit));
        editProductDescriptor.setSales(getNewSales(toAdd, productToEdit));
        editProductDescriptor.setThreshold(productToEdit.getThreshold());

        Product editedProduct = createEditedProduct(productToEdit, editProductDescriptor);
        if (!productToEdit.isSameProduct(editedProduct) && model.hasProduct(editedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.setProduct(productToEdit, editedProduct);
        model.updateFilteredProductList();

        return editedProduct;
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

        if (oldQuantity.compareTo(toAdd.getQuantity()) == 0) {
            throw new CommandException(Messages.MESSAGE_ZERO_PRODUCT_QUANTITY);
        } else if (oldQuantity.compareTo(toAdd.getQuantity()) < 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PRODUCT_QUANTITY,
                    oldQuantity.getValue(), productToEdit.getDescription().value));
        }

        return oldQuantity.minus(toAdd.getQuantity());
    }

    private Money getNewSales(Transaction toAdd, Product productToEdit) {
        Money oldSales = productToEdit.getMoney();
        Money newSales;

        newSales = oldSales.plus(toAdd.getMoney());

        return newSales;
    }
}

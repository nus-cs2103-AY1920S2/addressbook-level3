package seedu.address.logic.commands.exceptions;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_DATE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

/**
 * Buys the stated quantity of the good stated from the given supplier on the provided transaction date.
 * This batch of goods will be set to expire on the given expiry date, after which they will expire and
 * be removed from inventory.
 */
public class BuyCommand extends Command {
    public static final String COMMAND_WORD = "buy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Buys the stated "
            + "quantity of the good stated from the given supplier on the provided transaction date. "
            + "This batch of goods will be set to expire on the given expiry date, after which "
            + "they will expire and be removed from inventory. "
            + "Parameters: "
            + PREFIX_NAME + "SUPPLIER_NAME "
            + PREFIX_GOOD_NAME + "GOOD_NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE "
            + PREFIX_TRANSACTION_DATE + "TRANSACTION_DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Leong Fai Produce Co. "
            + PREFIX_GOOD_NAME + "Apples "
            + PREFIX_QUANTITY + "50 "
            + PREFIX_EXPIRY_DATE + "2020-02-02 "
            + PREFIX_TRANSACTION_DATE + "2020-01-01";

    public static final String MESSAGE_SUCCESS = "Bought %1$d %2$ss from %3$s on %4$s, expiring on %5$s";

    private String supplierName;
    private String goodName;
    // TODO: change these 2 to DateTime objects
    private String expiryDate;
    private String transactionDate;
    private int quantity;

    public BuyCommand(String supplierName, String goodName, int quantity,
                      String expiryDate, String transactionDate) {
        requireAllNonNull(supplierName, goodName, expiryDate, transactionDate);
        this.supplierName = supplierName;
        this.goodName = goodName;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.transactionDate = transactionDate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        Good newGood = new Good(new GoodName(goodName),
                new GoodQuantity(quantity));

        if (model.hasGood(newGood)) {
            increaseQuantity(model, newGood);
        } else {
            model.addGood(newGood);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                quantity, goodName, supplierName, transactionDate, expiryDate));
    }

    /**
     * Increases the quantity of an existing good in inventory with the same name as {@code newGood}
     * by the quantity in {@code newGood}
     * @param model underlying model to make modifications in
     * @param newGood contains the good name and quantity to increase by
     */
    private void increaseQuantity(Model model, Good newGood) {
        int oldGoodIndex = model.indexOfGood(newGood);
        Good oldGood = model.getFilteredGoodList().get(oldGoodIndex);

        int updatedQuantity = oldGood.getGoodQuantity().value() + newGood.getGoodQuantity().value();

        Good updatedGood = new Good(new GoodName(newGood.getGoodName().toString()),
                new GoodQuantity(updatedQuantity));

        model.setGood(oldGood, updatedGood);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyCommand)) {
            return false;
        }

        // state check
        BuyCommand e = (BuyCommand) other;
        return quantity == e.getQuantity()
                && supplierName.equals(e.getSupplierName())
                && goodName.equals(e.getGoodName())
                && expiryDate.equals(e.getExpiryDate())
                && transactionDate.equals(e.getTransactionDate());
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public int getQuantity() {
        return quantity;
    }
}

package seedu.address.model.transaction;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.FindTransactionCommandParser.TransactionType;
import seedu.address.model.good.GoodName;
import seedu.address.model.supplier.Name;

/**
 * Tests that a {@code Transaction} matches any of the conditions given.
 */
public class TransactionContainKeywordsPredicate implements Predicate<Transaction> {

    private TransactionType transactionType;
    private Name supplierName;
    private GoodName goodName;

    public TransactionContainKeywordsPredicate(TransactionType transactionType, Name supplierName, GoodName goodName) {
        this.transactionType = transactionType;
        this.supplierName = supplierName;
        this.goodName = goodName;
    }


    // fulfill all 3 tests
    @Override
    public boolean test(Transaction transaction) {
        return testTransactionType(transaction)
                && testSupplierName(transaction)
                && testGoodName(transaction);
    }

    /**
     * filters valid transaction by transaction type.
     *
     * @param transaction to be filtered.
     * @return true if the transactionType is unspecified or the type of transaction matches the transactionType.
     */
    private boolean testTransactionType(Transaction transaction) {
        // transactionType unspecified
        if (transactionType == null) {
            return true;
        }
        // type of transaction matches the transactionType
        return (transactionType.equals(TransactionType.BUY_TRANSACTION) && transaction instanceof BuyTransaction)
                || (transactionType.equals(TransactionType.SELL_TRANSACTION) && transaction instanceof SellTransaction);
    }

    /**
     * filters valid transaction by supplier name.
     *
     * @param transaction must be buy transaction to have a supplier.
     * @return true if the supplier name is unspecified or the buy transaction contains supplier that have name that
     * partial matches the keyword in supplierName.
     */
    private boolean testSupplierName(Transaction transaction) {
        // supplier name is unspecified
        if (supplierName == null) {
            return true;
        }
        // transaction type is invalid, since sellTransaction does not have a supplier
        if (transaction instanceof SellTransaction) {
            return false;
        }
        // valid transaction type with partial matching supplier name
        BuyTransaction buyTransaction = (BuyTransaction) transaction;
        return StringUtil.containsWordIgnoreCase(buyTransaction.getSupplier().getName().fullName,
                supplierName.fullName);
    }

    /**
     * filters valid transaction by good name.
     *
     * @param transaction to be filtered.
     * @return true if the good name is unspecified or the transaction contains good that have good name that
     * partial matches the keyword in goodName.
     */
    private boolean testGoodName(Transaction transaction) {
        // goodName is unspecified
        if (goodName == null) {
            return true;
        }
        // goodName partial matches
        return StringUtil.containsWordIgnoreCase(transaction.getGood().getGoodName().fullGoodName,
                goodName.fullGoodName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContainKeywordsPredicate // instanceof handles nulls
                && transactionType.equals(((TransactionContainKeywordsPredicate) other).transactionType))
                && supplierName.equals(((TransactionContainKeywordsPredicate) other).supplierName)
                && goodName.equals(((TransactionContainKeywordsPredicate) other).goodName); // state check
    }

}

package seedu.expensela.testutil;

import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Remark;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_NAME = "Pepperoni Pizza";
    public static final String DEFAULT_AMOUNT = "50.00";
    public static final String DEFAULT_DATE = "2020-03-18";
    public static final String DEFAULT_REMARK = "Split bill with Greg";
    public static final String DEFAULT_CATEGORY = "FOOD";

    private Name name;
    private Amount amount;
    private Date date;
    private Remark remark;
    private Category category;

    public TransactionBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT, false);
        date = new Date(DEFAULT_DATE);
        remark = new Remark(DEFAULT_REMARK);
        category = new Category(DEFAULT_CATEGORY);
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        name = transactionToCopy.getName();
        amount = transactionToCopy.getAmount();
        date = transactionToCopy.getDate();
        remark = transactionToCopy.getRemark();
        category = transactionToCopy.getCategory();
    }

    /**
     * Sets the {@code Name} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(String amount, boolean isPositive) {
        this.amount = new Amount(amount, isPositive);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    public Transaction build() {
        return new Transaction(name, amount, date, remark, category);
    }

}

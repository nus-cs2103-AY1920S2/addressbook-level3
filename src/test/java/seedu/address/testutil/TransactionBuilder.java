package seedu.address.testutil;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Income;
import seedu.address.model.transaction.Loan;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_DESC = "DEFAULT";
    public static final double DEFAULT_AMOUNT = 0;

    private Description description;
    private Amount amount;
    private Date date;
    private Tag tag;

    public TransactionBuilder() {
        description = new Description(DEFAULT_DESC);
        amount = new Amount(DEFAULT_AMOUNT);
        date = Date.getDefault();
        tag = Tag.getDefault();
    }

    /**
     * Initializes the TransactionBuilder with the data of
     * {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        description = transactionToCopy.getDescription();
        amount = transactionToCopy.getAmount();
        date = transactionToCopy.getDate();
        tag = transactionToCopy.getTag();
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code amount} into an {@code Amount} and set it to the
     * {@code Transaction} that we are building.
     * Does nothing if amount is invalid.
     */
    public TransactionBuilder withAmount(String amount) {
        try {
            this.amount = ParserUtil.parseAmount(amount);
            return this;
        } catch (ParseException e) {
            return this;
        }
    }

    /**
     * Parses the {@code date} into an {@code Date} and set it to the
     * {@code Transaction} that we are building.
     * Does nothing if date is invalid.
     */
    public TransactionBuilder withDate(String date) {
        try {
            this.date = ParserUtil.parseDate(date);
            return this;
        } catch (ParseException e) {
            return this;
        }
    }

    /**
     * Sets the {@code Tag} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withTag(String tag) {
        this.tag = new Tag(tag);
        return this;
    }

    /**
     * Returns a new Expense object with the builder's fields.
     */
    public Expense buildExpense() {
        return new Expense(description, amount, date, tag);
    }

    /**
     * Returns a new Income object with the builder's fields.
     */
    public Income buildIncome() {
        return new Income(description, amount, date, tag);
    }

    /**
     * Returns a new Debt object with the builder's fields (except tag).
     */
    public Debt buildDebt() {
        return new Debt(description, amount, date);
    }

    /**
     * Returns a new Loan object with the builder's fields (except tag).
     */
    public Loan buildLoan() {
        return new Loan(description, amount, date);
    }
}

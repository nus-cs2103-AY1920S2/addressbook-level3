package seedu.eylah.expensesplitter.testutil;

import java.math.BigDecimal;

import seedu.eylah.expensesplitter.model.person.Amount;

/**
 * A utility class to help with building Amount objects.
 */
public class AmountBuilder {

    public static final Double DEFAULT_DOUBLEAMOUNT_CHICKENRICE = 3.50;

    private BigDecimal amount;

    public AmountBuilder() {
        amount = new BigDecimal(DEFAULT_DOUBLEAMOUNT_CHICKENRICE);
    }

    /**
     * Sets the {@code BigDecimal} of the {@code Amount} that we are building.
     */
    public AmountBuilder withDoubleAmount(Double amount) {
        this.amount = new BigDecimal(amount);
        return this;
    }

    /**
     * Sets the {@code BigDecimal} of the {@code Amount} that we are building.
     */
    public AmountBuilder withStringAmount(String amount) {
        this.amount = new BigDecimal(amount);
        return this;
    }

    public Amount build() {
        return new Amount(amount);
    }


}

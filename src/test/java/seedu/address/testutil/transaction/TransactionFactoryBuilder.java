package seedu.address.testutil.transaction;

import seedu.address.commons.core.index.Index;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.TransactionFactory;
import seedu.address.model.transaction.TransactionQuantity;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;

/**
 * A utility class to help with building TransactionFactory objects.
 */
public class TransactionFactoryBuilder {

    public static final Index DEFAULT_CUSTOMER_INDEX = Index.fromOneBased(1);
    public static final Index DEFAULT_PRODUCT_INDEX = Index.fromOneBased(1);
    public static final String DEFAULT_DATETIME = "2020-01-01 10:00";
    public static final int DEFAULT_MONEY = 1;
    public static final int DEFAULT_QUANTITY = 1;
    public static final String DEFAULT_DESCRIPTION = "under promotion";

    private Index customerIndex;
    private Index productIndex;
    private DateTime dateTime;
    private Quantity quantity;
    private Money money;
    private Description description;

    public TransactionFactoryBuilder() {
        customerIndex = DEFAULT_CUSTOMER_INDEX;
        productIndex = DEFAULT_PRODUCT_INDEX;
        dateTime = new DateTime(DEFAULT_DATETIME);
        money = new Money(DEFAULT_MONEY);
        quantity = new TransactionQuantity(DEFAULT_QUANTITY);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the TransactionFactoryBuilder with the data of {@code transactionFactoryToCopy}.
     */
    public TransactionFactoryBuilder(TransactionFactory transactionFactoryToCopy) {
        customerIndex = transactionFactoryToCopy.getCustomerIndex();
        productIndex = transactionFactoryToCopy.getProductIndex();
        dateTime = transactionFactoryToCopy.getDateTime();
        money = transactionFactoryToCopy.getMoney();
        quantity = transactionFactoryToCopy.getQuantity();
        description = transactionFactoryToCopy.getDescription();
    }

    /**
     * Sets the {@code customerIndex} of the {@code TransactionFactory} that we are building.
     */
    public TransactionFactoryBuilder withCustomerIndex(Index customerIndex) {
        this.customerIndex = customerIndex;
        return this;
    }

    /**
     * Sets the {@code productIndex} of the {@code TransactionFactory} that we are building.
     */
    public TransactionFactoryBuilder withProductIndex(Index productIndex) {
        this.productIndex = productIndex;
        return this;
    }


    /**
     * Sets the {@code DateTime} of the {@code TransactionFactory} that we are building.
     */
    public TransactionFactoryBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Money} of the {@code TransactionFactory} that we are building.
     */
    public TransactionFactoryBuilder withMoney(int money) {
        this.money = new Money(money);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Transaction} that we are building.
     */
    public TransactionFactoryBuilder withQuantity(int quantity) {
        this.quantity = new TransactionQuantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} that we are building.
     */
    public TransactionFactoryBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} to N/A.
     */
    public TransactionFactoryBuilder withDescription() {
        description = new Description(Description.DEFAULT_VALUE);
        return this;
    }

    /**
     * Returns a transaction with the given attributes.
     */
    public TransactionFactory build() {
        return new TransactionFactory(customerIndex, productIndex, dateTime,
                quantity, money, description);
    }

}


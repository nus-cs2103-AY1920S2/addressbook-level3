package seedu.address.testutil.transaction;

import static seedu.address.testutil.customer.TypicalCustomers.ALICE;
import static seedu.address.testutil.product.TypicalProducts.BAG;

import java.util.UUID;

import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionQuantity;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final Customer DEFAULT_CUSTOMER = ALICE;
    public static final Product DEFAULT_PRODUCT = BAG;
    public static final String DEFAULT_DATETIME = "2020-01-01 10:00";
    public static final int DEFAULT_MONEY = 20;
    public static final int DEFAULT_QUANTITY = 20;
    public static final String DEFAULT_DESCRIPTION = "under promotion";


    // Identity fields
    private Customer customer;
    private Product product;
    private UUID productId;
    private UUID customerId;
    private DateTime dateTime;

    // Data fields
    private Money money;
    private Quantity quantity;
    private Description description;

    public TransactionBuilder() {
        customer = DEFAULT_CUSTOMER;
        product = DEFAULT_PRODUCT;
        customerId = DEFAULT_CUSTOMER.getId();
        productId = DEFAULT_PRODUCT.getId();
        dateTime = new DateTime(DEFAULT_DATETIME);
        money = new Money(DEFAULT_MONEY);
        quantity = new TransactionQuantity(DEFAULT_QUANTITY);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        customer = transactionToCopy.getCustomer();
        product = transactionToCopy.getProduct();
        customerId = transactionToCopy.getCustomerId();
        productId = transactionToCopy.getProductId();
        dateTime = transactionToCopy.getDateTime();
        money = transactionToCopy.getMoney();
        quantity = transactionToCopy.getQuantity();
        description = transactionToCopy.getDescription();
    }

    /**
     * Sets the {@code Customer} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
        return this;
    }

    /**
     * Sets the {@code Product} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withProduct(Product product) {
        this.product = product;
        this.productId = product.getId();
        return this;
    }


    /**
     * Sets the {@code DateTime} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Money} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withMoney(int money) {
        this.money = new Money(money);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withQuantity(int quantity) {
        this.quantity = new TransactionQuantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Returns a transaction with the given attributes.
     */
    public Transaction build() {
        return new Transaction(customer, product, customerId, productId, dateTime,
                quantity, money, description);
    }
}

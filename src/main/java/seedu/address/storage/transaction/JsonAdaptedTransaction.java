package seedu.address.storage.transaction;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionQuantity;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.storage.customer.JsonAdaptedCustomer;
import seedu.address.storage.product.JsonAdaptedProduct;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final JsonAdaptedCustomer customer;
    private final JsonAdaptedProduct product;
    private final String productId;
    private final String customerId;
    private final String dateTime;
    private final String quantity;
    private final String money;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("customer") JsonAdaptedCustomer customer,
                                  @JsonProperty("product") JsonAdaptedProduct product,
                                  @JsonProperty("customerId") String customerId,
                                  @JsonProperty("productId") String productId,
                               @JsonProperty("dateTime") String dateTime, @JsonProperty("quantity") String quantity,
                               @JsonProperty("money") String money, @JsonProperty("description") String description) {
        this.customer = customer;
        this.product = product;
        this.customerId = customerId;
        this.productId = productId;
        this.dateTime = dateTime;
        this.quantity = quantity;
        this.money = money;
        this.description = description;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        customer = new JsonAdaptedCustomer(source.getCustomer());
        product = new JsonAdaptedProduct(source.getProduct());
        customerId = source.getCustomerId().toString();
        productId = source.getProductId().toString();
        dateTime = source.getDateTime().toString();
        quantity = source.getQuantity().toString();
        money = source.getMoney().toString();
        description = source.getDescription().toString();
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public Transaction toModelType() throws IllegalValueException {
        final Customer modelCustomer = getCustomer();
        final Product modelProduct = getProduct();
        final UUID modelCustomerId = getCustomerId();
        final UUID modelProductId = getProductId();
        final DateTime modelDateTime = getDateTime();
        final Quantity modelQuantity = getQuantity();
        final Money modelMoney = getMoney();
        final Description modelDescription = getDescription();

        return new Transaction(modelCustomer, modelProduct, modelCustomerId, modelProductId,
                modelDateTime, modelQuantity, modelMoney, modelDescription);
    }

    private Customer getCustomer() throws IllegalValueException {
        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        return customer.toModelType();
    }

    private Product getProduct() throws IllegalValueException {
        if (product == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        return product.toModelType();
    }

    private UUID getCustomerId() throws IllegalValueException {
        if (customerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        return UUID.fromString(customerId);
    }

    private UUID getProductId() throws IllegalValueException {
        if (productId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        return UUID.fromString(productId);
    }

    private DateTime getDateTime() throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(dateTime);
    }

    private Quantity getQuantity() throws IllegalValueException {
        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!TransactionQuantity.isValidFormat(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!TransactionQuantity.isValidValue(Integer.parseInt(quantity))) {
            throw new IllegalValueException(TransactionQuantity.MESSAGE_CONSTRAINTS_VALUE);
        }
        return new TransactionQuantity(quantity);
    }

    private Money getMoney() throws IllegalValueException {
        if (money == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Money.class.getSimpleName()));
        }
        if (!Money.isValidMoney(money)) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!Money.isValidAmount(Integer.parseInt(money))) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS_VALUE);
        }
        return new Money(money);
    }

    private Description getDescription() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }
}

package seedu.address.storage.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customer.Customer;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Money;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Description;
import seedu.address.model.util.Quantity;
import seedu.address.storage.customer.JsonAdaptedCustomer;
import seedu.address.storage.product.JsonAdaptedProduct;

import java.util.UUID;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final JsonAdaptedCustomer customer;
    private final JsonAdaptedProduct product;
    private final String productId;
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
                                  @JsonProperty("productId") String productId,
                               @JsonProperty("dateTime") String dateTime, @JsonProperty("quantity") String quantity,
                               @JsonProperty("money") String money, @JsonProperty("description") String description) {
        this.customer = customer;
        this.product = product;
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
        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final Customer modelCustomer = customer.toModelType();

        if (product == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final Product modelProduct = product.toModelType();

        if (productId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final UUID modelProductId = UUID.fromString(productId);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (money == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Money.class.getSimpleName()));
        }
        if (!Money.isValidMoney(money)) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS);
        }
        final Money modelMoney = new Money(money);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        return new Transaction(modelCustomer, modelProduct, modelProductId,
                modelDateTime, modelQuantity, modelMoney, modelDescription);
    }

}

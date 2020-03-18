package seedu.address.storage.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Money;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Description;
import seedu.address.model.util.Quantity;

import javax.crypto.spec.DESedeKeySpec;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String customer;
    private final String product;
    private final String dateTime;
    private final String quantity;
    private final String money;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("customer") String customer, @JsonProperty("product") String product,
                               @JsonProperty("dateTime") String dateTime, @JsonProperty("quantity") String quantity,
                               @JsonProperty("money") String money, @JsonProperty("description") String description) {
        this.customer = customer;
        this.product = product;
        this.dateTime = dateTime;
        this.quantity = quantity;
        this.money = money;
        this.description = description;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        customer = source.getCustomer();
        product = source.getProduct();
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
        //        if (!Name.isValidName(name)) {
        //            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        //        }
        final String modelCustomer = customer;

        if (product == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        //        if (!Phone.isValidPhone(phone)) {
        //            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        //        }
        final String modelProduct = product;

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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        return new Transaction(modelCustomer, modelProduct, modelDateTime, modelQuantity, modelMoney, modelDescription);
    }

}

package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customer.*;
import seedu.address.model.product.Description;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.DateTime;
import seedu.address.model.transaction.Money;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Quantity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Customer}.
 */
class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String customer;
    private final String product;
    private final String dateTime;
    private final String quantity;
    private final String money;
    private final StringBuilder description = new StringBuilder("");

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("customer") String customer, @JsonProperty("product") String product,
                               @JsonProperty("email") String dateTime, @JsonProperty("quantity") String quantity,
                               @JsonProperty("money") String money, @JsonProperty("description") String description) {
        this.customer = customer;
        this.product = product;
        this.dateTime = dateTime;
        this.quantity = quantity;
        this.money = money;
        if (description != null) {
            this.description.append(description);
        }
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        customer = source.getCustomer();
        product = source.getProduct();
        dateTime = source.getDateTime().toString();
        quantity = source.getQuantity().toString();
        money = source.getMoney().toString();
        description.append(source.getDescription());
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName()));
        }
        if (!Address.isValidAddress(quantity)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (money == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Money.class.getSimpleName()));
        }
        if (!Address.isValidAddress(money)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Money modelMoney = new Money(money);

        final String modelDescription = description.toString();
        return new Transaction(modelCustomer, modelProduct, modelDateTime, modelQuantity, modelMoney, modelDescription);
    }

}

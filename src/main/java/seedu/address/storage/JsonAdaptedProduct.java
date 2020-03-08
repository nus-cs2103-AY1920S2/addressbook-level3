package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;
import seedu.address.model.product.*;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    private final String description;
    private final String price;
    private final String quantity;
    private final String sales;

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("description") String description, @JsonProperty("price") String price,
                             @JsonProperty("quantity") String quantity, @JsonProperty("sales") String sales) {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sales = sales;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedProduct(Product source) {
        description = source.getDescription().value;
        price = source.getPrice().value;
        quantity = source.getQuantity().value;
        sales = source.getSales().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Product toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (sales == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sales.class.getSimpleName()));
        }
        if (!Sales.isValidSales(sales)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Sales modelSales = new Sales(sales);

        return new Product(modelDescription, modelPrice, modelQuantity, modelSales);
    }

}

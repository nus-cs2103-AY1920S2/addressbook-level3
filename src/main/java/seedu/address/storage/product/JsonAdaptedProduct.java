package seedu.address.storage.product;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.product.Sales;
import seedu.address.model.util.Description;
import seedu.address.model.util.Quantity;

/**
 * Jackson-friendly version of {@link Product}.
 */
public class JsonAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    private final String description;
    private final String price;
    private final String quantity;
    private final String sales;
    private final String id;

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("description") String description, @JsonProperty("price") String price,
                             @JsonProperty("quantity") String quantity, @JsonProperty("sales") String sales,
                              @JsonProperty("id") String id) {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sales = sales;
        this.id = id;
    }

    /**
     * Converts a given {@code Product} into this class for Jackson use.
     */
    public JsonAdaptedProduct(Product source) {
        description = source.getDescription().value;
        price = source.getPrice().value;
        quantity = source.getQuantity().toString();
        sales = source.getSales().value;
        id = source.getId().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Product} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Product toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                            Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                            Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (sales == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sales.class.getSimpleName()));
        }
        if (!Sales.isValidSales(sales)) {
            throw new IllegalValueException(Sales.MESSAGE_CONSTRAINTS);
        }
        final Sales modelSales = new Sales(sales);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sales.class.getSimpleName()));
        }
        final UUID modelId = UUID.fromString(id);

        return new Product(modelDescription, modelPrice, modelQuantity, modelSales, modelId);
    }

}

package seedu.address.storage.product;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.model.util.QuantityThreshold;

/**
 * Jackson-friendly version of {@link Product}.
 */
public class JsonAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    private final String description;
    private final String costPrice;
    private final String price;
    private final String quantity;
    private final String sales;
    private final String id;
    private final String threshold;

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("description") String description,
                              @JsonProperty("costPrice") String costPrice, @JsonProperty("price") String price,
                             @JsonProperty("quantity") String quantity, @JsonProperty("sales") String sales,
                              @JsonProperty("threshold") String threshold, @JsonProperty("id") String id) {
        this.description = description;
        this.costPrice = costPrice;
        this.price = price;
        this.quantity = quantity;
        this.sales = sales;
        this.threshold = threshold;
        this.id = id;
    }

    /**
     * Converts a given {@code Product} into this class for Jackson use.
     */
    public JsonAdaptedProduct(Product source) {
        description = source.getDescription().value;
        costPrice = source.getCostPrice().value;
        price = source.getPrice().value;
        quantity = source.getQuantity().toString();
        sales = source.getMoney().toString();
        threshold = source.getThreshold().value;
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

        if (costPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CostPrice.class.getSimpleName()));
        }
        if (!CostPrice.isValidPrice(costPrice)) {
            throw new IllegalValueException(CostPrice.MESSAGE_CONSTRAINTS);
        }
        final CostPrice modelCostPrice = new CostPrice(costPrice);

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
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!Quantity.isValidValue(Integer.parseInt(quantity))) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS_VALUE);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (sales == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Money.class.getSimpleName()));
        }
        if (!Money.isValidMoney(sales)) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!Money.isValidAmount(Integer.parseInt(sales))) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS_VALUE);
        }
        final Money modelSales = new Money(sales);

        if (threshold == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuantityThreshold.class.getSimpleName()));
        }
        if (!QuantityThreshold.isValidQuantity(threshold)) {
            throw new IllegalValueException(QuantityThreshold.MESSAGE_CONSTRAINTS);
        }
        final QuantityThreshold modelQuantityThreshold = new QuantityThreshold(threshold);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final UUID modelId = UUID.fromString(id);

        return new Product(modelId, modelDescription, modelCostPrice, modelPrice, modelQuantity,
                modelSales, modelQuantityThreshold, 1);
    }

}

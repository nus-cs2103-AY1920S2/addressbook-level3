package seedu.address.storage.product;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductQuantity;
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
    private final String money;
    private final String id;
    private final String threshold;

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("description") String description,
                              @JsonProperty("costPrice") String costPrice, @JsonProperty("price") String price,
                             @JsonProperty("quantity") String quantity, @JsonProperty("money") String money,
                              @JsonProperty("threshold") String threshold, @JsonProperty("id") String id) {
        this.description = description;
        this.costPrice = costPrice;
        this.price = price;
        this.quantity = quantity;
        this.money = money;
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
        money = source.getMoney().toString();
        threshold = source.getThreshold().toString();
        id = source.getId().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Product} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Product toModelType() throws IllegalValueException {
        final UUID modelId = getUuid();
        final Description modelDescription = getDescription();
        final CostPrice modelCostPrice = getCostPrice();
        final Price modelPrice = getPrice();
        final Quantity modelQuantity = getQuantity();
        final Money modelMoney = getMoney();
        final QuantityThreshold modelQuantityThreshold = getQuantityThreshold();

        return new Product(modelId, modelDescription, modelCostPrice, modelPrice, modelQuantity,
                modelMoney, modelQuantityThreshold, 1);
    }

    private UUID getUuid() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        return UUID.fromString(id);
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

    private CostPrice getCostPrice() throws IllegalValueException {
        if (costPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CostPrice.class.getSimpleName()));
        }
        if (!CostPrice.isValidPrice(costPrice)) {
            throw new IllegalValueException(CostPrice.MESSAGE_CONSTRAINTS);
        }
        return new CostPrice(costPrice);
    }

    private Price getPrice() throws IllegalValueException {
        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(price);
    }

    private Quantity getQuantity() throws IllegalValueException {
        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!ProductQuantity.isValidFormat(quantity)) {
            throw new IllegalValueException(ProductQuantity.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!ProductQuantity.isValidValue(Integer.parseInt(quantity))) {
            throw new IllegalValueException(ProductQuantity.MESSAGE_CONSTRAINTS_VALUE);
        }
        return new ProductQuantity(quantity);
    }

    private Money getMoney() throws IllegalValueException {
        if (money == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Money.class.getSimpleName()));
        }
        if (!Money.isValidMoney(money)) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!Money.isValidAmount(Integer.parseInt(money))) {
            throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS_VALUE);
        }
        return new Money(money);
    }

    private QuantityThreshold getQuantityThreshold() throws IllegalValueException {
        if (threshold == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuantityThreshold.class.getSimpleName()));
        }
        if (!QuantityThreshold.isValidQuantity(threshold)) {
            throw new IllegalValueException(QuantityThreshold.MESSAGE_CONSTRAINTS);
        }
        return new QuantityThreshold(threshold);
    }
}

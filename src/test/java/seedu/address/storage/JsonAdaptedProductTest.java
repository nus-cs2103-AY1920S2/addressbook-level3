package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.product.JsonAdaptedProduct.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.product.TypicalProducts.BAG;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.CostPrice;
import seedu.address.model.product.Price;
import seedu.address.model.util.Description;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;
import seedu.address.model.util.QuantityThreshold;
import seedu.address.storage.product.JsonAdaptedProduct;

public class JsonAdaptedProductTest {
    private static final String INVALID_COSTPRICE = "-12345";
    private static final String INVALID_PRICE = "+651234";
    private static final String INVALID_QUANTITY = " ";
    private static final String INVALID_SALES = "example.com";

    private static final String VALID_DESCRIPTION = BAG.getDescription().toString();
    private static final String VALID_COSTPRICE = BAG.getCostPrice().toString();
    private static final String VALID_PRICE = BAG.getPrice().toString();
    private static final String VALID_QUANTITY = BAG.getQuantity().toString();
    private static final String VALID_SALES = BAG.getMoney().toString();
    private static final String VALID_THRESHOLD = BAG.getThreshold().toString();
    private static final String VALID_ID = BAG.getId().toString();

    @Test
    public void toModelType_validProductDetails_returnsProduct() throws Exception {
        JsonAdaptedProduct bag = new JsonAdaptedProduct(BAG);
        assertEquals(BAG, bag.toModelType());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(null, VALID_COSTPRICE, VALID_PRICE,
                VALID_QUANTITY, VALID_SALES, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_invalidCostPrice_throwsIllegalValueException() {
        JsonAdaptedProduct product =
                new JsonAdaptedProduct(VALID_DESCRIPTION, INVALID_COSTPRICE, VALID_PRICE, VALID_QUANTITY,
                        VALID_SALES, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = CostPrice.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedProduct product =
                new JsonAdaptedProduct(VALID_DESCRIPTION, VALID_COSTPRICE, INVALID_PRICE, VALID_QUANTITY,
                        VALID_SALES, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(VALID_DESCRIPTION, VALID_COSTPRICE, null,
                VALID_QUANTITY, VALID_SALES, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedProduct product =
                new JsonAdaptedProduct(VALID_DESCRIPTION, VALID_COSTPRICE, VALID_PRICE,
                        INVALID_QUANTITY, VALID_SALES, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(VALID_DESCRIPTION, VALID_COSTPRICE, VALID_PRICE, null,
                VALID_SALES, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_invalidSales_throwsIllegalValueException() {
        JsonAdaptedProduct product =
                new JsonAdaptedProduct(VALID_DESCRIPTION, VALID_COSTPRICE, VALID_PRICE,
                        VALID_QUANTITY, INVALID_SALES, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = Money.MESSAGE_CONSTRAINTS_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullSales_throwsIllegalValueException() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(VALID_DESCRIPTION, VALID_COSTPRICE, VALID_PRICE,
                VALID_QUANTITY, null, VALID_THRESHOLD, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Money.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }

    @Test
    public void toModelType_nullThreshold_throwsIllegalValueException() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(VALID_DESCRIPTION, VALID_COSTPRICE, VALID_PRICE,
                VALID_QUANTITY, VALID_SALES, null, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, QuantityThreshold.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, product::toModelType);
    }
}

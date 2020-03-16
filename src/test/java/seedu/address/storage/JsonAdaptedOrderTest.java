package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.comment.Comment;
import seedu.address.model.order.Address;
import seedu.address.model.order.Name;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.Warehouse;

public class JsonAdaptedOrderTest {
    private static final String INVALID_TID = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TIMESTAMP = "2019-02-32 1500";
    private static final String INVALID_WAREHOUSE = "";
    private static final String INVALID_COD = "1";
    private static final String INVALID_COMMENT = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TID = BENSON.getTID().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_TIMESTAMP = BENSON.getTimestamp().toString();
    private static final String VALID_WAREHOUSE = BENSON.getWarehouse().toString();
    private static final String VALID_COD = BENSON.getCash().toString();
    private static final String VALID_COMMENT = BENSON.getComment().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(BENSON);
        assertEquals(BENSON, order.toModelType());
    }

    @Test
    public void toModelType_nullTID_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(null, VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidTID_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_TID, VALID_NAME, VALID_PHONE,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, INVALID_NAME, VALID_PHONE,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, null, VALID_PHONE, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, INVALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, INVALID_PHONE,
                VALID_TIMESTAMP, VALID_ADDRESS, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, null, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE,
                INVALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, null,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidTimeStamp_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE,
                VALID_ADDRESS, INVALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = TimeStamp.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullTimeStamp_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                null, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeStamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidWarehouse_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                VALID_TIMESTAMP, INVALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Warehouse.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullWarehouse_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                VALID_TIMESTAMP, null, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Warehouse.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidCashOnDelivery_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, INVALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Warehouse.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullCashOnDelivery_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                VALID_WAREHOUSE, null, VALID_COD, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Warehouse.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidComment_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_TIMESTAMP,
                        VALID_WAREHOUSE, VALID_COD, INVALID_COMMENT, VALID_TAGS);
        String expectedMessage = Comment.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_TIMESTAMP,
                        VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, invalidTags);
        assertThrows(IllegalValueException.class, order::toModelType);
    }

}

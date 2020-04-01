package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedReturnOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReturnOrders.BENSON_RETURN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.parcel.comment.Comment;
import seedu.address.model.parcel.itemtype.TypeOfItem;
import seedu.address.model.parcel.parcelattributes.Address;
import seedu.address.model.parcel.parcelattributes.Email;
import seedu.address.model.parcel.parcelattributes.Name;
import seedu.address.model.parcel.parcelattributes.Phone;
import seedu.address.model.parcel.parcelattributes.TimeStamp;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.parcelattributes.Warehouse;

public class JsonAdaptedReturnOrderTest {
    private static final String INVALID_TID = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "1234gg.com";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TIMESTAMP = "2019-02-32 1500";
    private static final String INVALID_WAREHOUSE = "";
    private static final String INVALID_COMMENT = " ";
    private static final String INVALID_TYPE = "#bottle";

    private static final String VALID_TID = BENSON_RETURN.getTid().toString();
    private static final String VALID_NAME = BENSON_RETURN.getName().toString();
    private static final String VALID_PHONE = BENSON_RETURN.getPhone().toString();
    private static final String VALID_EMAIL = BENSON_RETURN.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON_RETURN.getAddress().toString();
    private static final String VALID_TIMESTAMP = BENSON_RETURN.getTimestamp().toString();
    private static final String VALID_WAREHOUSE = BENSON_RETURN.getWarehouse().toString();
    private static final String VALID_COMMENT = BENSON_RETURN.getComment().toString();
    private static final String VALID_TYPE = BENSON_RETURN.getItemType().toString();
    private static final boolean VALID_DELIVERY_STATUS = BENSON_RETURN.isDelivered();

    @Test
    public void toModelType_validReturnOrderDetails_returnsOrder() throws Exception {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(BENSON_RETURN);
        assertEquals(returnOrder.toModelType(), BENSON_RETURN);
    }

    @Test
    public void toModelType_nullTid_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(null, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TransactionId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidTid_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(INVALID_TID, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = TransactionId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, INVALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, INVALID_PHONE,
                VALID_EMAIL, VALID_TIMESTAMP, VALID_ADDRESS, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE,
                INVALID_EMAIL, VALID_TIMESTAMP, VALID_ADDRESS, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidTimeStamp_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = TimeStamp.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_nullTimeStamp_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeStamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidWarehouse_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, INVALID_WAREHOUSE, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Warehouse.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_nullWarehouse_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder = new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, null, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Warehouse.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidComment_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder =
                new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMESTAMP, VALID_WAREHOUSE, INVALID_COMMENT, VALID_TYPE,
                        VALID_DELIVERY_STATUS);
        String expectedMessage = Comment.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

    @Test
    public void toModelType_invalidItemType_throwsIllegalValueException() {
        JsonAdaptedReturnOrder returnOrder =
                new JsonAdaptedReturnOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, INVALID_TYPE,
                        VALID_DELIVERY_STATUS);
        String expectedMessage = TypeOfItem.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, returnOrder::toModelType);
    }

}

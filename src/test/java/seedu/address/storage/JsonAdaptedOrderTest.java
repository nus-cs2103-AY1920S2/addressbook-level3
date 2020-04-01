package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Parcel.comment.Comment;
import seedu.address.model.Parcel.itemtype.TypeOfItem;
import seedu.address.model.Parcel.ParcelAttributes.Address;
import seedu.address.model.Parcel.order.CashOnDelivery;
import seedu.address.model.Parcel.ParcelAttributes.Email;
import seedu.address.model.Parcel.ParcelAttributes.Name;
import seedu.address.model.Parcel.ParcelAttributes.Phone;
import seedu.address.model.Parcel.ParcelAttributes.TimeStamp;
import seedu.address.model.Parcel.ParcelAttributes.TransactionId;
import seedu.address.model.Parcel.ParcelAttributes.Warehouse;

public class JsonAdaptedOrderTest {
    private static final String INVALID_TID = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "1234gg.com";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TIMESTAMP = "2019-02-32 1500";
    private static final String INVALID_WAREHOUSE = "";
    private static final String INVALID_COD = "1";
    private static final String INVALID_COMMENT = " ";
    private static final String INVALID_TYPE = "#bottle";

    private static final String VALID_TID = BENSON.getTid().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_TIMESTAMP = BENSON.getTimestamp().toString();
    private static final String VALID_WAREHOUSE = BENSON.getWarehouse().toString();
    private static final String VALID_COD = BENSON.getCash().toString();
    private static final String VALID_COMMENT = BENSON.getComment().toString();
    private static final String VALID_TYPE = BENSON.getItemType().toString();
    private static final boolean VALID_DELIVERY_STATUS = BENSON.isDelivered();

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(BENSON);
        assertEquals(order.toModelType(), BENSON);
    }

    @Test
    public void toModelType_nullTid_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(null, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TransactionId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidTid_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = TransactionId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, INVALID_COD, VALID_COMMENT, VALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_TIMESTAMP, VALID_ADDRESS, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_TIMESTAMP, VALID_ADDRESS, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidTimeStamp_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = TimeStamp.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullTimeStamp_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeStamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidWarehouse_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, INVALID_WAREHOUSE, VALID_COD, VALID_COMMENT, VALID_TYPE,
                VALID_DELIVERY_STATUS);
        String expectedMessage = Warehouse.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullWarehouse_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, null, VALID_COD, VALID_COMMENT, VALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Warehouse.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidCashOnDelivery_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, INVALID_COD, VALID_COMMENT, VALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = CashOnDelivery.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullCashOnDelivery_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, null, VALID_COMMENT, VALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CashOnDelivery.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidComment_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TIMESTAMP,
                        VALID_WAREHOUSE, VALID_COD, INVALID_COMMENT, VALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = Comment.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidItemType_throwsIllegalValueException() {
        JsonAdaptedOrder order =
                new JsonAdaptedOrder(VALID_TID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TIMESTAMP,
                        VALID_WAREHOUSE, VALID_COD, VALID_COMMENT, INVALID_TYPE, VALID_DELIVERY_STATUS);
        String expectedMessage = TypeOfItem.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

}

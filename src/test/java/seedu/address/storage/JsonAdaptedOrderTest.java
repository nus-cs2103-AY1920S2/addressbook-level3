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
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Phone;
import seedu.address.model.order.Warehouse;

public class JsonAdaptedOrderTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TIMESTAMP = "2019-02-29 1500";
    private static final String INVALID_WAREHOUSE = "";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_COMMENT = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_TIMESTAMP = BENSON.getTimestamp().toString();
    private static final String VALID_WAREHOUSE = BENSON.getWarehouse().toString();
    private static final String VALID_COMMENT = BENSON.getComment().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedOrder person = new JsonAdaptedOrder(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_TIMESTAMP, VALID_ADDRESS, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_TIMESTAMP, VALID_ADDRESS, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TIMESTAMP, VALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidWarehouse_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, INVALID_WAREHOUSE, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = Warehouse.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullWarehouse_throwsIllegalValueException() {
        JsonAdaptedOrder person = new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TIMESTAMP, null, VALID_COMMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Warehouse.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidComment_throwsIllegalValueException() {
        JsonAdaptedOrder person =
                new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TIMESTAMP,
                        VALID_WAREHOUSE, INVALID_COMMENT, VALID_TAGS);
        String expectedMessage = Comment.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedOrder person =
                new JsonAdaptedOrder(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TIMESTAMP,
                        VALID_WAREHOUSE, VALID_COMMENT, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}

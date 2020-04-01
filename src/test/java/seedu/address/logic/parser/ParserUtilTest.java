package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Parcel.comment.Comment;
import seedu.address.model.Parcel.itemtype.TypeOfItem;
import seedu.address.model.Parcel.ParcelAttributes.Address;
import seedu.address.model.Parcel.ParcelAttributes.Name;
import seedu.address.model.Parcel.ParcelAttributes.Phone;
import seedu.address.model.Parcel.ParcelAttributes.TimeStamp;
import seedu.address.model.Parcel.ParcelAttributes.Warehouse;

public class ParserUtilTest {
    private static final String INVALID_TID = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_COMMENT = " ";
    private static final String INVALID_COD = "AAA";
    private static final String INVALID_TYPE = "#glass";
    private static final String INVALID_WAREHOUSE_ADDRESS = "";
    private static final String INVALID_DELIVERY_TIMESTAMP = "2019-02-29 1350";

    private static final String VALID_TID = "A94848484";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_DELIVERY_TIMESTAMP = "2020-05-20 1500";
    private static final String VALID_WAREHOUSE_ADDRESS = "Goose Road, #01-93";
    private static final String VALID_COD = "$4";
    private static final String VALID_COMMENT = "Leave at outside";
    private static final String VALID_TYPE_1 = "glass";
    private static final String VALID_TYPE_2 = "plastic";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(
                Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ORDER, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ORDER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseTimeStamp_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTimeStamp((String) null));
    }

    @Test
    public void parseTimeStamp_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTimeStamp(INVALID_DELIVERY_TIMESTAMP));
    }

    @Test
    public void parseTimeStamp_validValueWithoutWhitespace_returnsTimeStamp() throws Exception {
        TimeStamp expectedTimeStamp = new TimeStamp(VALID_DELIVERY_TIMESTAMP);
        assertEquals(expectedTimeStamp, ParserUtil.parseTimeStamp(VALID_DELIVERY_TIMESTAMP));
    }

    @Test
    public void parseTimeStamp_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String timeStampWithWhitespace = WHITESPACE + VALID_DELIVERY_TIMESTAMP + WHITESPACE;
        TimeStamp expectedTimeStamp = new TimeStamp(VALID_DELIVERY_TIMESTAMP);
        assertEquals(expectedTimeStamp, ParserUtil.parseTimeStamp(timeStampWithWhitespace));
    }

    @Test
    public void parseWarehouse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWarehouse(null));
    }

    @Test
    public void parseWarehouse_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWarehouse(INVALID_WAREHOUSE_ADDRESS));
    }

    @Test
    public void parseWarehouse_validValueWithoutWhitespace_returnsWarehouse() {
        Warehouse expectedWarehouse = new Warehouse(VALID_WAREHOUSE_ADDRESS);
        try {
            assertEquals(expectedWarehouse, ParserUtil.parseWarehouse(VALID_WAREHOUSE_ADDRESS));
        } catch (ParseException pe) {
            fail("Should not throw ParseException for parseWarehouse function");
        }
    }

    @Test
    public void parseWarehouse_validValueWithWhitespace_returnsTrimmedWarehouse() {
        String warehouseAddressWithWhitespace = WHITESPACE + VALID_WAREHOUSE_ADDRESS + WHITESPACE;
        Warehouse expectedWarehouse = new Warehouse(VALID_WAREHOUSE_ADDRESS);
        try {
            assertEquals(expectedWarehouse, ParserUtil.parseWarehouse(warehouseAddressWithWhitespace));
        } catch (ParseException pe) {
            fail("Should not throw ParseException for parseWarehouse function");
        }
    }

    @Test
    public void parseTid_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTid((String) null));
    }

    @Test
    public void parseTid_invalidValue_throwsNullPointerException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTid(INVALID_TID));
    }

    @Test
    public void parseCash_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCash(INVALID_COD));
    }

    @Test
    public void parseCash_null_throwsParseException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCash((String) null));
    }

    @Test
    public void parseComment_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseComment((String) null));
    }

    @Test
    public void parseComment_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseComment(INVALID_COMMENT));
    }

    @Test
    public void parseComment_validValueWithoutWhitespace_returnsComment() throws Exception {
        Comment expectedComment = new Comment(VALID_COMMENT);
        assertEquals(expectedComment, ParserUtil.parseComment(VALID_COMMENT));
    }

    @Test
    public void parseComment_validValueWithWhitespace_returnsTrimmedComment() throws Exception {
        String commentWithWhitespace = WHITESPACE + VALID_COMMENT + WHITESPACE;
        Comment expectedComment = new Comment(VALID_COMMENT);
        assertEquals(expectedComment, ParserUtil.parseComment(commentWithWhitespace));
    }

    @Test
    public void parseItemType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseItemType(null));
    }

    @Test
    public void parseItemType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseItemType(INVALID_TYPE));
    }

    @Test
    public void parseItemType_validValueWithoutWhitespace_returnsItemType() throws Exception {
        TypeOfItem expectedType = new TypeOfItem(VALID_TYPE_1);
        assertEquals(expectedType, ParserUtil.parseItemType(VALID_TYPE_1));
    }

    @Test
    public void parseItemType_validValueWithWhitespace_returnsTrimmedItemType() throws Exception {
        String typeWithWhitespace = WHITESPACE + VALID_TYPE_1 + WHITESPACE;
        TypeOfItem expectedType = new TypeOfItem(VALID_TYPE_1);
        assertEquals(expectedType, ParserUtil.parseItemType(typeWithWhitespace));
    }

}

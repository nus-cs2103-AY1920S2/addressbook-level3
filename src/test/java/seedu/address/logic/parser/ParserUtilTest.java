package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.comment.Comment;
import seedu.address.model.order.Address;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.Warehouse;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_COMMENT = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_WAREHOUSE_ADDRESS = "";
    private static final String INVALID_DELIVERY_TIMESTAMP = "2019-02-29 1350";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_DELIVERY_TIMESTAMP = "2020-02-29 1350";
    private static final String VALID_WAREHOUSE_ADDRESS = "Goose Road, #01-93";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_COMMENT = "Leave at outside";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

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
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
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
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}

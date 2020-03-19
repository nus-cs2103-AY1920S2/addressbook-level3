package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    private static final String VALID_DATE = "2020-08-20";
    private static final String VALID_ACTIVITY_NAME = "CS2103T TP";
    private static final String VALID_NOTE = "Finish milestone v1.2 by next wednesday."
            + "prepare for new features.";
    private static final String VALID_PRIORITY = "3";

    private static final String INVALID_DATE = "20-12-2020";
    private static final String INVALID_ACTIVITY_NAME = " ";
    private static final String INVALID_NOTE = "\t\r";
    private static final String INVALID_PRIORITY = "-2";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
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

    @Test
    public void parseDate_validDateWithoutWhitespaces_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validDateWithWhitespaces_returnsDate() throws Exception {
        String dateWithWhiteSpace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhiteSpace));
    }

    @Test
    public void parseDate_invalidDateWithoutWhitespaces_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_invalidDateWithWhitespaces_throwsParseException() {
        String dateWithWhiteSpace = WHITESPACE + INVALID_DATE + WHITESPACE;
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(dateWithWhiteSpace));
    }

    @Test
    public void parseActivityName_validNameWithoutWhitespaces_returnsName() throws Exception {
        seedu.address.model.activity.Name expectedName = new seedu.address.model.activity.Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseActivityName(VALID_NAME));
    }

    @Test
    public void parseActivityName_validNameWithWhitespaces_returnsName() throws Exception {
        String nameWithWhiteSpaces = WHITESPACE + VALID_ACTIVITY_NAME + WHITESPACE;
        seedu.address.model.activity.Name expectedName = new seedu.address.model.activity.Name(VALID_ACTIVITY_NAME);
        assertEquals(expectedName, ParserUtil.parseActivityName(nameWithWhiteSpaces));
    }

    @Test
    public void parseActivityName_invalidNameWithoutWhitespaces_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseActivityName(INVALID_ACTIVITY_NAME));
    }

    @Test
    public void parseActivityName_invalidNameWithWhitespaces_throwsParseException() {
        String nameWithWhiteSpaces = WHITESPACE + INVALID_ACTIVITY_NAME + WHITESPACE;
        assertThrows(ParseException.class, () -> ParserUtil.parseActivityName(nameWithWhiteSpaces));
    }

    @Test
    public void parseNote_validNoteWithoutWhitespaces_returnsNote() throws Exception {
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(VALID_NOTE));
    }

    @Test
    public void parseNote_validNoteWithWhitespaces_returnsNote() throws Exception {
        String noteWithWhiteSpaces = WHITESPACE + VALID_NOTE + WHITESPACE;
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhiteSpaces));
    }

    @Test
    public void parseNote_invalidNoteWithoutWhitespaces_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(INVALID_NOTE));
    }

    @Test
    public void parseNote_invalidNoteWithWhitespaces_throwsParseException() {
        String noteWithWhiteSpaces = WHITESPACE + INVALID_NOTE + WHITESPACE;
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(noteWithWhiteSpaces));
    }

    @Test
    public void parsePriority_validPriorityWithoutWhitespaces_returnsPriority() throws Exception {
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriority(VALID_PRIORITY));
    }

    @Test
    public void parsePriority_validPriorityWithWhitespaces_returnsPriority() throws Exception {
        String priorityWithWhitespaces = WHITESPACE + VALID_PRIORITY + WHITESPACE;
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriority(priorityWithWhitespaces));
    }

    @Test
    public void parsePriority_invalidPriorityWithoutWhitespaces_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority(INVALID_PRIORITY));
    }

    @Test
    public void parsePriority_invalidPriorityWithWhitespaces_throwsParseException() {
        String priorityWithWhitespaces = WHITESPACE + INVALID_PRIORITY + WHITESPACE;
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority(priorityWithWhitespaces));
    }
}

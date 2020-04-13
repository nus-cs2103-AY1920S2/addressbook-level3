package seedu.nova.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.category.Category;
import seedu.nova.model.person.Email;
import seedu.nova.model.person.Name;
import seedu.nova.model.person.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_WEEK = "-1";
    private static final String INVALID_TASK = "-1";
    private static final String INVALID_PROJECT = "IPP";
    private static final String INVALID_TASKDESC = "";
    private static final String INVALID_NOTE = "";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "classmate";
    private static final String VALID_TAG_2 = "teammate";

    private static final String VALID_DATE = "2020-04-10";
    private static final String VALID_TIME = "14:00";
    private static final String VALID_DAY = "FRIDAY";

    private static final String INVALID_DATE = "2020-04-31";
    private static final String INVALID_TIME = "25:00";
    private static final String INVALID_DAY = "Freeday";

    private static final String WHITESPACE = " \t\r\n";

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
        Category expectedCategory = new Category(VALID_TAG_1);
        assertEquals(expectedCategory, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Category expectedCategory = new Category(VALID_TAG_1);
        assertEquals(expectedCategory, ParserUtil.parseTag(tagWithWhitespace));
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
        Set<Category> actualCategorySet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Category> expectedCategorySet = new HashSet<Category>(Arrays.asList(new Category(VALID_TAG_1),
                new Category(VALID_TAG_2)));

        assertEquals(expectedCategorySet, actualCategorySet);
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_validValue_success() throws Exception {
        LocalTime expectedTime = LocalTime.parse(VALID_TIME);

        // No whitespaces
        assertEquals(expectedTime, ParserUtil.parseTime("14:00"));

        // Leading and trailing whitespaces
        assertEquals(expectedTime, ParserUtil.parseTime("  14:00  "));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_validValue_success() throws Exception {
        // No whitespaces
        LocalDate expectedDate = LocalDate.parse(VALID_DATE);

        assertEquals(expectedDate, ParserUtil.parseDate("2020-04-10"));

        // Leading and trailing whitespaces
        assertEquals(expectedDate, ParserUtil.parseDate("  2020-04-10  "));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDay_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDay((String) null));
    }

    @Test
    public void parseDay_validValue_success() throws Exception {
        DayOfWeek expectedDay = DayOfWeek.valueOf(VALID_DAY);

        // No whitespaces
        assertEquals(expectedDay, ParserUtil.parseDay("Friday"));

        // Leading and trailing whitespaces
        assertEquals(expectedDay, ParserUtil.parseDay("  Friday  "));
    }

    @Test
    public void parseDay_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay(INVALID_DAY));
    }

    @Test
    public void parseWeek_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek(INVALID_WEEK));
    }

    @Test
    public void parseTask_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTask(INVALID_TASK));
    }

    @Test
    public void parseProject_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProject(INVALID_PROJECT));
    }

    @Test
    public void parseTaskDesc_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTaskDesc(INVALID_TASKDESC));
    }

    @Test
    public void parseNote_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(INVALID_NOTE));
    }
}

package tatracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.index.Index;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.SessionType;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;
import tatracker.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_MATRIC = "!0123456&";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_MATRIC = "A0123456J";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DATE = "2020-02-20";
    private static final String VALID_TIME = "15:33";
    private static final String VALID_TYPE = "consultation";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseDate_validInput_success() throws ParseException {
        LocalDate expectedDate = LocalDate.of(2020, 02, 20);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("2020-02-31"));
    }

    @Test
    public void parseDate_wrongFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate("20-03-2020"));
    }

    @Test
    public void parseTime_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime("6:30"));
    }

    @Test
    public void parseTime_validInput_success() throws ParseException {
        LocalTime expectedTime = LocalTime.of(15, 33);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parseSessionType_validInput_success() throws ParseException {
        SessionType expectedType = SessionType.getSessionType("consultation");
        assertEquals(expectedType, ParserUtil.parseSessionType(VALID_TYPE));
    }

    @Test
    public void parseSessionTypeCaps_validInput_success() throws ParseException {
        SessionType expectedType = SessionType.getSessionType("CONSULTATION");
        assertEquals(expectedType, ParserUtil.parseSessionType(VALID_TYPE));
    }

    @Test
    public void parseSessionType_invalidInput_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseSessionType("consult"));
    }

    @Test
    public void parseNumWeeks_validInput_success() throws ParseException {
        assertEquals(1, ParserUtil.parseNumWeeks("1"));
    }

    @Test
    public void parseNumWeeks_invalidLetters_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseNumWeeks("a"));
    }

    @Test
    public void parseNumWeeks_invalidNegativeNum_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseNumWeeks("-1"));
    }


    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, Index.MESSAGE_CONSTRAINTS, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_STUDENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_STUDENT, ParserUtil.parseIndex("  1  "));
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
    public void parseMatric_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMatric((String) null));
    }

    @Test
    public void parseMatric_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMatric(INVALID_MATRIC));
    }

    @Test
    public void parseMatric_validValueWithoutWhitespace_returnsMatric() throws Exception {
        Matric expectedMatric = new Matric(VALID_MATRIC);
        assertEquals(expectedMatric, ParserUtil.parseMatric(VALID_MATRIC));
    }

    @Test
    public void parseM_validValueWithWhitespace_returnsTrimmedMatric() throws Exception {
        String matricWithWhitespace = WHITESPACE + VALID_MATRIC + WHITESPACE;
        Matric expectedMatric = new Matric(VALID_MATRIC);
        assertEquals(expectedMatric, ParserUtil.parseMatric(matricWithWhitespace));
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
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseRating_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRating(null));
    }

    @Test
    public void parseRating_signedValue_throwsParseException() {
        // Cannot have '+' or '-' symbol when parsing
        assertThrows(ParseException.class, () -> ParserUtil.parseRating("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseRating("+3"));
    }

    @Test
    public void parseRating_zeroValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRating("0"));
    }

    @Test
    public void parseRating_outOfRangeValue_throwsParseException() {
        // rating is not between 1 - 5
        assertThrows(ParseException.class, () -> ParserUtil.parseRating("6"));
    }

    @Test
    public void parseRating_invalidValue_throwsParseException() {
        // trailing characters
        assertThrows(ParseException.class, () -> ParserUtil.parseRating("3 3333"));

        // non-numeric
        assertThrows(ParseException.class, () -> ParserUtil.parseRating("3potato"));

        // symbols
        assertThrows(ParseException.class, () -> ParserUtil.parseRating("4+-"));
    }

    @Test
    public void parseRating_validValueWithoutWhitespace_returnsRating() throws Exception {
        Rating expectedRating = new Rating(3);
        assertEquals(expectedRating, ParserUtil.parseRating("3"));
    }

    @Test
    public void parseRating_validValueWithWhitespace_returnsTrimmedRating() throws Exception {
        String ratingWithWhitespace = WHITESPACE + "3" + WHITESPACE;
        Rating expectedRating = new Rating(3);
        assertEquals(expectedRating, ParserUtil.parseRating(ratingWithWhitespace));
    }
}

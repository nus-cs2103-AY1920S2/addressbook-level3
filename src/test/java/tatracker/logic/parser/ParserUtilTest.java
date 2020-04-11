package tatracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.parser.ParserUtil.MESSAGE_INVALID_UNSIGNED_INT;
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
import tatracker.commons.util.DateTimeUtil;
import tatracker.logic.commands.commons.GotoCommand.Tab;
import tatracker.logic.commands.sort.SortType;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.TaTracker;
import tatracker.model.group.GroupType;
import tatracker.model.session.Session;
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

    private static final String INVALID_DATE = "04/04/2020";
    private static final String INVALID_TIME = "4pm";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_MATRIC = "A0123456J";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_DATE = "2020-04-04";
    private static final String VALID_TIME = "16:00";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseUnsignedInteger_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, () -> ParserUtil.parseUnsignedInteger("10 a"));

        // - Signed
        assertThrows(ParseException.class, () -> ParserUtil.parseUnsignedInteger("-10"));

        // + Signed
        assertThrows(ParseException.class, () -> ParserUtil.parseUnsignedInteger("+10"));
    }

    @Test
    public void parseUnsignedInteger_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUnsignedInteger(null));
    }

    @Test
    public void parseUnsignedInteger_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_UNSIGNED_INT, ()
            -> ParserUtil.parseUnsignedInteger(Long.toString((long) Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseUnsignedInteger_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(1, ParserUtil.parseUnsignedInteger("1"));

        // Leading and trailing whitespaces
        assertEquals(1, ParserUtil.parseUnsignedInteger("  1  "));

        // Zero
        assertEquals(0, ParserUtil.parseUnsignedInteger("  0  "));
    }

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, Index.MESSAGE_CONSTRAINTS, ()
            -> ParserUtil.parseIndex(Long.toString((long) Integer.MAX_VALUE + 1)));
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
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
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
    public void parseName_validValueWithManyWhitespaces_returnsCapitalizedName() throws Exception {
        String nameWithManyWhitespaces = "lee      kuan    yew";
        Name expectedName = new Name("Lee Kuan Yew");
        assertEquals(expectedName, ParserUtil.parseName(nameWithManyWhitespaces));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
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
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
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
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMatric(null));
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
    public void parseMatric_validValueWithWhitespace_returnsTrimmedMatric() throws Exception {
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

    @Test
    public void parseDate_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, DateTimeUtil.CONSTRAINTS_DATE, ()
            -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        LocalDate expectedDate = LocalDate.of(2020, 4, 4);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        LocalDate expectedDate = LocalDate.of(2020, 4, 4);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseTime_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, DateTimeUtil.CONSTRAINTS_TIME, ()
            -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime(null));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        LocalTime expectedTime = LocalTime.of(16, 0);
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        LocalTime expectedTime = LocalTime.of(16, 0);
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parseSessionType_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, SessionType.MESSAGE_CONSTRAINTS, ()
            -> ParserUtil.parseSessionType("junk"));
    }

    @Test
    public void parseSessionType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSessionType(null));
    }

    @Test
    public void parseSessionType_validValueWithoutWhitespace_returnsSessionType() throws Exception {
        assertEquals(SessionType.TUTORIAL, ParserUtil.parseSessionType("tutorial"));
        assertEquals(SessionType.LAB, ParserUtil.parseSessionType("lab"));
        assertEquals(SessionType.CONSULTATION, ParserUtil.parseSessionType("consultation"));
        assertEquals(SessionType.GRADING, ParserUtil.parseSessionType("grading"));
        assertEquals(SessionType.PREPARATION, ParserUtil.parseSessionType("preparation"));
        assertEquals(SessionType.OTHER, ParserUtil.parseSessionType("other"));
    }

    @Test
    public void parseSessionType_validValueWithWhitespace_returnsSessionType() throws Exception {
        String typeWithWhitespace = WHITESPACE + "tutorial" + WHITESPACE;
        SessionType expectedSessionType = SessionType.TUTORIAL;
        assertEquals(expectedSessionType, ParserUtil.parseSessionType(typeWithWhitespace));
    }

    @Test
    public void parseGroupType_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, GroupType.MESSAGE_CONSTRAINTS, ()
            -> ParserUtil.parseGroupType("junk"));
    }

    @Test
    public void parseGroupType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroupType(null));
    }

    @Test
    public void parseGroupType_validValueWithoutWhitespace_returnsGroupType() throws Exception {
        assertEquals(GroupType.TUTORIAL, ParserUtil.parseGroupType("tutorial"));
        assertEquals(GroupType.LAB, ParserUtil.parseGroupType("lab"));
        assertEquals(GroupType.RECITATION, ParserUtil.parseGroupType("recitation"));
        assertEquals(GroupType.OTHER, ParserUtil.parseGroupType("other"));
    }

    @Test
    public void parseGroupType_validValueWithWhitespace_returnsGroupType() throws Exception {
        String typeWithWhitespace = WHITESPACE + "tutorial" + WHITESPACE;
        GroupType expectedGroupType = GroupType.TUTORIAL;
        assertEquals(expectedGroupType, ParserUtil.parseGroupType(typeWithWhitespace));
    }

    @Test
    public void parseSortType_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, SortType.MESSAGE_CONSTRAINTS, ()
            -> ParserUtil.parseSortType("junk"));
    }

    @Test
    public void parseSortType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortType(null));
    }

    @Test
    public void parseSortType_validValueWithoutWhitespace_returnsSortType() throws Exception {
        assertEquals(SortType.ALPHABETIC, ParserUtil.parseSortType("alphabetically"));
        assertEquals(SortType.ALPHABETIC, ParserUtil.parseSortType("alpha"));
        assertEquals(SortType.ALPHABETIC, ParserUtil.parseSortType("alphabetical"));

        assertEquals(SortType.MATRIC, ParserUtil.parseSortType("matric"));

        assertEquals(SortType.RATING_ASC, ParserUtil.parseSortType("rating asc"));
        assertEquals(SortType.RATING_ASC, ParserUtil.parseSortType("asc"));

        assertEquals(SortType.RATING_DESC, ParserUtil.parseSortType("rating desc"));
        assertEquals(SortType.RATING_DESC, ParserUtil.parseSortType("desc"));
    }

    @Test
    public void parseSortType_validValueWithWhitespace_returnsSortType() throws Exception {
        String typeWithWhitespace = WHITESPACE + "matric" + WHITESPACE;
        SortType expectedSortType = SortType.MATRIC;
        assertEquals(expectedSortType, ParserUtil.parseSortType(typeWithWhitespace));
    }

    @Test
    public void parseTabName_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, Tab.MESSAGE_CONSTRAINTS, ()
            -> ParserUtil.parseTabName("junk"));
    }

    @Test
    public void parseTabName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTabName(null));
    }

    @Test
    public void parseTabName_validValueWithoutWhitespace_returnsTab() throws Exception {
        assertEquals(Tab.STUDENT, ParserUtil.parseTabName("student"));
        assertEquals(Tab.SESSION, ParserUtil.parseTabName("session"));
        assertEquals(Tab.CLAIMS, ParserUtil.parseTabName("claims"));
    }

    @Test
    public void parseTabName_validValueWithWhitespace_returnsTab() throws Exception {
        String tabWithWhitespace = WHITESPACE + "claims" + WHITESPACE;
        Tab expectedTab = Tab.CLAIMS;
        assertEquals(expectedTab, ParserUtil.parseTabName(tabWithWhitespace));
    }

    @Test
    public void parseNumWeeks_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, Session.CONSTRAINTS_RECURRING_WEEKS, ()
            -> ParserUtil.parseNumWeeks("10 a"));

        // - Signed
        assertThrows(ParseException.class, Session.CONSTRAINTS_RECURRING_WEEKS, ()
            -> ParserUtil.parseNumWeeks("-10"));

        // + Signed
        assertThrows(ParseException.class, Session.CONSTRAINTS_RECURRING_WEEKS, ()
            -> ParserUtil.parseNumWeeks("+10"));
    }

    @Test
    public void parseNumWeeks_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNumWeeks(null));
    }

    @Test
    public void parseNumWeeks_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, Session.CONSTRAINTS_RECURRING_WEEKS, ()
            -> ParserUtil.parseNumWeeks(Long.toString((long) Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseNumWeeks_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(1, ParserUtil.parseNumWeeks("1"));

        // Leading and trailing whitespaces
        assertEquals(1, ParserUtil.parseNumWeeks("  1  "));

        // Zero
        assertEquals(0, ParserUtil.parseNumWeeks("  0  "));
    }

    @Test
    public void parseRate_invalidInput_throwsParseException() {
        // Invalid characters
        assertThrows(ParseException.class, TaTracker.CONSTRAINTS_RATE, ()
            -> ParserUtil.parseRate("10 a"));

        // - Signed
        assertThrows(ParseException.class, TaTracker.CONSTRAINTS_RATE, ()
            -> ParserUtil.parseRate("-10"));

        // + Signed
        assertThrows(ParseException.class, TaTracker.CONSTRAINTS_RATE, ()
            -> ParserUtil.parseRate("+10"));

        // Zero
        assertThrows(ParseException.class, TaTracker.CONSTRAINTS_RATE, ()
            -> ParserUtil.parseRate("0"));
    }

    @Test
    public void parseRate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRate(null));
    }

    @Test
    public void parseRate_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, TaTracker.CONSTRAINTS_RATE, ()
            -> ParserUtil.parseRate(Long.toString((long) Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseRate_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(1, ParserUtil.parseRate("1"));

        // Leading and trailing whitespaces
        assertEquals(1, ParserUtil.parseRate("  1  "));
    }
}

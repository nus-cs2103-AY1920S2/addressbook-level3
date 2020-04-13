package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.lesson.LessonType;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.Description;

public class ParserUtilTest {
    private static final String INVALID_MODULE_CODE = "#CS2103T";
    private static final String INVALID_DESCRIPTION = "very very very very"
            + " very very very very very very"
            + " very very very long description";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_OFFICE = " ";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_MODULE_CODE_1 = "CS2103T";
    private static final String VALID_MODULE_CODE_2 = "CS2101";
    private static final String VALID_DESCRIPTION = "Software Engineering";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_OFFICE = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";

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
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseModuleCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCode(null));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleCode(INVALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithoutWhitespace_returnsModuleCode() throws Exception {
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE_1);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(VALID_MODULE_CODE_1));
    }

    @Test
    public void parseModuleCode_validValueWithWhitespace_returnsTrimmedModuleCode() throws Exception {
        String moduleCodeWithWhitespace = WHITESPACE + VALID_MODULE_CODE_1 + WHITESPACE;
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE_1);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(moduleCodeWithWhitespace));
    }

    @Test
    public void parseDescription_null_returnsNullDescription() throws Exception {
        Description expectedDescription = new Description(null);
        assertEquals(expectedDescription, ParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
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
    public void parsePhone_null_returnsNullPhone() throws Exception {
        Phone expectedPhone = new Phone(null);
        assertEquals(expectedPhone, ParserUtil.parsePhone(null));
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
    public void parseOffice_null_returnsNullOffice() throws Exception {
        Office expectedOffice = new Office(null);
        assertEquals(expectedOffice, ParserUtil.parseOffice(null));
    }

    @Test
    public void parseOffice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOffice(INVALID_OFFICE));
    }

    @Test
    public void parseOffice_validValueWithoutWhitespace_returnsOffice() throws Exception {
        Office expectedOffice = new Office(VALID_OFFICE);
        assertEquals(expectedOffice, ParserUtil.parseOffice(VALID_OFFICE));
    }

    @Test
    public void parseAOffice_validValueWithWhitespace_returnsTrimmedOffice() throws Exception {
        String officeWithWhitespace = WHITESPACE + VALID_OFFICE + WHITESPACE;
        Office expectedOffice = new Office(VALID_OFFICE);
        assertEquals(expectedOffice, ParserUtil.parseOffice(officeWithWhitespace));
    }

    @Test
    public void parseEmail_null_returnsNullEmail() throws Exception {
        Email expectedEmail = new Email(null);
        assertEquals(expectedEmail, ParserUtil.parseEmail(null));
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
    public void parseModuleCodes_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCodes((String) null));
    }

    @Test
    public void parseModuleCodes_collectionWithInvalidModuleCodes_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleCodes(Arrays.asList(VALID_MODULE_CODE_1,
                INVALID_MODULE_CODE)));
    }

    @Test
    public void parseModuleCodes_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseModuleCodes(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseModuleCodes_collectionWithValidModuleCodes_returnsModuleCodeSet() throws Exception {
        Set<ModuleCode> actualModuleCodeSet = ParserUtil.parseModuleCodes(Arrays.asList(
                VALID_MODULE_CODE_1, VALID_MODULE_CODE_2));
        Set<ModuleCode> expectedModuleCodeSet = new HashSet<ModuleCode>(Arrays.asList(
                new ModuleCode(VALID_MODULE_CODE_1), new ModuleCode(VALID_MODULE_CODE_2)));

        assertEquals(expectedModuleCodeSet, actualModuleCodeSet);
    }

    @Test
    public void parseLessonType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLessonType(null));
    }

    @Test
    public void parseLessonType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLessonType("ABC"));
    }

    @Test
    public void parseLessonType_validValueWithoutWhitespace_returnsLessonType() throws Exception {
        assertEquals(LessonType.LEC, ParserUtil.parseLessonType("LEC"));
    }

    @Test
    public void parseLessonType_validValueWithWhitespace_returnsTrimmedLessonType() throws Exception {
        assertEquals(LessonType.TUT, ParserUtil.parseLessonType("  TUT  "));
    }

    @Test
    public void parseDay_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDay(null));
    }

    @Test
    public void parseDay_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDay("ABC 08:00 10:00"));
    }

    @Test
    public void parseDay_validValueWithoutWhitespace_returnsDay() throws Exception {
        assertEquals(DayOfWeek.valueOf("MONDAY"), ParserUtil.parseDay("MONDAY"));
    }

    @Test
    public void parseDay_validValueWithWhitespace_returnsTrimmedDay() throws Exception {
        assertEquals(DayOfWeek.valueOf("SUNDAY"), ParserUtil.parseDay("  SUNDAY  "));
    }

    @Test
    public void parseDayAndTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDayAndTime(null));
    }

    @Test
    public void parseDayAndTime_validValue_returnsDayAndTime() throws Exception {
        assertEquals(new DayAndTime("MONDAY 08:00 10:00"), ParserUtil.parseDayAndTime("MONDAY 08:00 10:00"));
    }

    @Test
    public void parseDayAndTime_validValue_returnsTrimmedDayAndTime() throws Exception {
        assertEquals(new DayAndTime("MONDAY 08:00 10:00"), ParserUtil.parseDayAndTime("   MONDAY 08:00 10:00   "));
    }

    @Test
    public void parseDayAndTime_invalidValue_throwsParseException() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseDayAndTime("MONDAY 23:00 25:00"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDayAndTime("FREEDAY 20:00 22:00"));
    }

    @Test
    public void parseVenue_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseVenue(null));
    }

    @Test
    public void parseVenue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseVenue(""));
    }

    @Test
    public void parseVenue_validValueWithoutWhitespace_returnsVenue() throws Exception {
        assertEquals("COM2-01-01", ParserUtil.parseVenue("COM2-01-01"));
    }

    @Test
    public void parseVenue_validValueWithWhitespace_returnsTrimmedVenue() throws Exception {
        assertEquals("COM2-01-01", ParserUtil.parseVenue("  COM2-01-01  "));
    }

    @Test
    public void parseWeek_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWeek(null));
    }

    @Test
    public void parseWeek_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek("prev"));
    }

    @Test
    public void parseWeek_validValueWithoutWhitespace_returnsWeek() throws Exception {
        assertEquals(Calendar.getNowCalendar(), ParserUtil.parseWeek("this"));
    }

    @Test
    public void parseWeek_validValueWithWhitespace_returnsTrimmedWeek() throws Exception {
        assertEquals(Calendar.getNextWeekCalendar(), ParserUtil.parseWeek("  next  "));
    }

}

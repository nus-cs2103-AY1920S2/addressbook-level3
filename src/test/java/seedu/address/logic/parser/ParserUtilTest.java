package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_COURSE_FOCUS_AREA;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MODULE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_SEMESTER;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Name;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String VALID_NAME = "Rachel Walker";
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
    public void parseModuleCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCode(null));
    }

    @Test
    public void parseModuleCode_emptyString_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_MISSING_MODULE, () -> ParserUtil.parseModuleCode(""));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        String[] invalidModuleCodes = {"1101", "A1101", "1101X", "A1101X", "ABCDE1010", "ABC101"};
        for (String moduleCode: invalidModuleCodes) {
            assertThrows(ParseException.class, MESSAGE_INVALID_MODULE, () -> ParserUtil.parseModuleCode(moduleCode));
        }
    }

    @Test
    public void parseSemester_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSemester(null));
    }

    @Test
    public void parseSemester_emptyString_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_MISSING_SEMESTER, () -> ParserUtil.parseSemester(""));
    }

    @Test
    public void parseSemester_string_throwsParseException() {
        assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_SEMESTER, () -> ParserUtil.parseSemester("abc"));
    }

    @Test
    public void parseCourseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCourseName(null));
    }

    @Test
    public void parseCourseName_emptyString_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_MISSING_COURSE, () -> ParserUtil.parseCourseName(""));
    }

    @Test
    public void parseCourseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_COURSE, () -> ParserUtil.parseCourseName("course x"));
    }

    @Test
    public void parseFocusArea_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFocusArea(null));
    }

    @Test
    public void parseFocusArea_emptyString_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_MISSING_COURSE_FOCUS_AREA, () -> ParserUtil.parseFocusArea(""));
    }
}

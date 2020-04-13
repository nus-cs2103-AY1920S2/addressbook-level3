package nasa.logic.parser;

import static nasa.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nasa.logic.parser.exceptions.ParseException;
import nasa.model.activity.Date;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleName;

public class ParserUtilTest {

    private static final String INVALID_DATE = "2020-12-20 12:59";
    private static final String INVALID_ACTIVITY_NAME = " ";
    private static final String INVALID_NOTE = "\t\r";
    private static final String INVALID_PRIORITY = "-2";
    private static final String INVALID_MODULE_NAME = "C@!;'[]";

    private static final String VALID_DATE = "20-08-2020 23:59";
    private static final String VALID_ACTIVITY_NAME = "CS2103T TP";
    private static final String VALID_NOTE = "Finish milestone v1.2 by next wednesday."
            + "prepare for new features.";
    private static final String VALID_PRIORITY = "3";
    private static final String VALID_MODULE_NAME = "Computer organisation";
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
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleName((String) null));
    }

    @Test
    public void parseModuleName_validModuleNameWithoutWhitespaces_returnsModuleName() throws Exception {
        ModuleName expectedModuleName = new ModuleName(VALID_MODULE_NAME);
        assertEquals(expectedModuleName, ParserUtil.parseModuleName(VALID_MODULE_NAME));
    }

    @Test
    public void parseModuleName_validModuleNameWithWhitespaces_returnsModuleName() throws Exception {
        String moduleNameWithWhitespaces = WHITESPACE + VALID_MODULE_NAME + WHITESPACE;
        ModuleName expectedModuleName = new ModuleName(VALID_MODULE_NAME);
        assertEquals(expectedModuleName, ParserUtil.parseModuleName(moduleNameWithWhitespaces));
    }

    @Test
    public void parseModuleName_invalidModuleNameWithoutWhitespaces_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleName(INVALID_MODULE_NAME));
    }

    @Test
    public void parseModuleName_invalidModuleNameWithWhitespaces_throwsParseException() {
        String moduleNameWithWhitespaces = WHITESPACE + INVALID_MODULE_NAME + WHITESPACE;
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleName(moduleNameWithWhitespaces));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
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
    public void parseActivityName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseActivityName(null));
    }

    @Test
    public void parseActivityName_validNameWithoutWhitespaces_returnsName() throws Exception {
        Name expectedName = new Name(VALID_ACTIVITY_NAME);
        assertEquals(expectedName, ParserUtil.parseActivityName(VALID_ACTIVITY_NAME));
    }

    @Test
    public void parseActivityName_validNameWithWhitespaces_returnsName() throws Exception {
        String nameWithWhiteSpaces = WHITESPACE + VALID_ACTIVITY_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_ACTIVITY_NAME);
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
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote(null));
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
    public void parsePriority_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePriority(null));
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

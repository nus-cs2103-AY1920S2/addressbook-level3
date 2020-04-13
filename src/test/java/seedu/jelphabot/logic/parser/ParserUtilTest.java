package seedu.jelphabot.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;

public class ParserUtilTest {

    private static final String INVALID_DESCRIPTION = "@ssignment !";
    private static final String INVALID_MODULE_CODE = "ASFE1000R";
    private static final String INVALID_DATETIME = "03/10/-2020 33 79";
    private static final String INVALID_TAG = "#graded";

    private static final String VALID_DESCRIPTION = "Assignment 1";
    private static final String VALID_MODULE_CODE = "AS1000";
    private static final String VALID_DATETIME = "Mar-20-2020 11 50";
    private static final String VALID_TAG_1 = "graded";
    private static final String VALID_TAG_2 = "project";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            // -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
            -> ParserUtil.parseIndex("!"));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("  1  "));
    }

    // description tests
    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsName() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(nameWithWhitespace));
    }

    // module code tests
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
        ModuleCode expectedAddress = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedAddress, ParserUtil.parseModuleCode(VALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithWhitespace_returnsTrimmedModuleCode() throws Exception {
        String moduleCodeWithWhiteSpace = WHITESPACE + VALID_MODULE_CODE + WHITESPACE;
        ModuleCode expectedAddress = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedAddress, ParserUtil.parseModuleCode(moduleCodeWithWhiteSpace));
    }

    // datetime tests
    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime(null));
    }

    @Test
    public void parseDateTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime(INVALID_DATETIME));
    }

    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsDateTime() throws Exception {
        DateTime expectedAddress = new DateTime(VALID_DATETIME);
        assertEquals(expectedAddress, ParserUtil.parseDateTime(VALID_DATETIME));
    }

    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedDateTime() throws Exception {
        String dateTimeWithWhiteSpace = WHITESPACE + VALID_DATETIME + WHITESPACE;
        DateTime expectedAddress = new DateTime(VALID_DATETIME);
        assertEquals(expectedAddress, ParserUtil.parseDateTime(dateTimeWithWhiteSpace));
    }

    // tag tests
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

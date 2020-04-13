package seedu.foodiebot.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.foodiebot.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.foodiebot.testutil.Assert.assertThrows;
import static seedu.foodiebot.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.foodiebot.commons.core.index.Index;
import seedu.foodiebot.logic.parser.exceptions.ParseException;
import seedu.foodiebot.model.canteen.Name;
import seedu.foodiebot.model.tag.Tag;
import seedu.foodiebot.model.util.SampleDataUtil;

public class ParserUtilTest {
    private static final String INVALID_NAME = "T@eDeck";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#asian";

    private static final String VALID_NAME = "The Deck";
    private static final String VALID_TAG_1 = "western";
    private static final String VALID_TAG_2 = "asian";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(
            ParseException.class,
            MESSAGE_INVALID_INDEX, (
            ) -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("  1  "));
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
        assertThrows(
            ParseException.class, (
            ) -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet =
            new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseFilterTag_emptyString_throwsParseException() {
        assertThrows(ParseException.class , () -> ParserUtil.parseFilterTag(""));
    }

    @Test
    public void parseFilterTag_nonEmptyString_returnsString() throws Exception {
        String testString = "test";
        String resultString = ParserUtil.parseFilterTag(testString);
        assertEquals(testString, resultString);
    }

    @Test
    public void parseStallIndex_invalidIndex_throwsParseException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
            ParserUtil.parseStallIndex("1000", SampleDataUtil.getSampleCanteens()[0]));
    }

    @Test
    public void parseStallIndex_validIndex_returnsIndex() throws ParseException {
        assertEquals(Index.fromOneBased(1),
            ParserUtil.parseStallIndex("1", SampleDataUtil.getSampleCanteens()[0]));
    }

    @Test
    public void parseBlockName() throws ParseException {
        assertEquals("UHC",
            ParserUtil.parseBlockName("UHC"));
        assertThrows(ParseException.class, () ->
            ParserUtil.parseBlockName("Invalid"));
    }

    @Test
    public void parseCanteenIndex() throws ParseException {
        assertEquals(Index.fromOneBased(1),
            ParserUtil.parseCanteenIndex("1"));
        assertThrows(ParseException.class, () ->
            ParserUtil.parseBlockName("Invalid"));
    }

    @Test
    public void parseCanteenName() throws ParseException {
        assertEquals("The Deck",
            ParserUtil.parseCanteenName("The Deck"));
        assertThrows(ParseException.class, () ->
            ParserUtil.parseCanteenName("Invalid"));
    }
}

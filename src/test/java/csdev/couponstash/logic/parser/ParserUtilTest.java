package csdev.couponstash.logic.parser;

import static csdev.couponstash.logic.parser.ParserUtil.MESSAGE_INDEX_OVERFLOW;
import static csdev.couponstash.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.coupon.Limit;
import csdev.couponstash.model.coupon.Name;
import csdev.couponstash.model.coupon.PromoCode;
import csdev.couponstash.model.coupon.Usage;
import csdev.couponstash.model.tag.Tag;
import csdev.couponstash.testutil.TypicalIndexes;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_USAGE = "-1";
    private static final String INVALID_LIMIT = "asdf";
    private static final String INVALID_TAG = "#friend";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PROMO_CODE = "STASH4LYFE";
    private static final String VALID_USAGE = "1";
    private static final String VALID_LIMIT = "5";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        String index = Long.toString(Integer.MAX_VALUE + 1);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_INDEX, index), ()
            -> ParserUtil.parseIndex(index));
    }

    @Test
    public void parseIndex_integerOverFlow_throwsOverflowException() {
        assertThrows(ParseException.class, MESSAGE_INDEX_OVERFLOW, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1L)));
    }

    @Test
    public void parseIndex_longOverFlow_throwsOverflowException() {
        assertThrows(ParseException.class, MESSAGE_INDEX_OVERFLOW, ()
            -> ParserUtil.parseIndex("9223372036854775808"));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_COUPON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_COUPON, ParserUtil.parseIndex("  1  "));
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
    public void parsePromoCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePromoCode((String) null));
    }

    @Test
    public void parsePromoCode_validValueWithoutWhitespace_returnsPromoCode() throws Exception {
        PromoCode expectedPromoCode = new PromoCode(VALID_PROMO_CODE);
        assertEquals(expectedPromoCode, ParserUtil.parsePromoCode(VALID_PROMO_CODE));
    }

    @Test
    public void parsePromoCode_validValueWithWhitespace_returnsTrimmedPromoCode() throws Exception {
        String promoCodeWithWhitespace = WHITESPACE + VALID_PROMO_CODE + WHITESPACE;
        PromoCode expectedPromoCode = new PromoCode(VALID_PROMO_CODE);
        assertEquals(expectedPromoCode, ParserUtil.parsePromoCode(promoCodeWithWhitespace));
    }

    @Test
    public void parseUsage_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUsage((String) null));
    }

    @Test
    public void parseUsage_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseUsage(INVALID_USAGE));
    }

    @Test
    public void parseUsage_validValueWithoutWhitespace_returnsUsage() throws Exception {
        Usage expectedUsage = new Usage(VALID_USAGE);
        assertEquals(expectedUsage, ParserUtil.parseUsage(VALID_USAGE));
    }

    @Test
    public void parseUsage_validValueWithWhitespace_returnsTrimmedUsage() throws Exception {
        String usageWithWhitespace = WHITESPACE + VALID_USAGE + WHITESPACE;
        Usage expectedUsage = new Usage(VALID_USAGE);
        assertEquals(expectedUsage, ParserUtil.parseUsage(usageWithWhitespace));
    }

    @Test
    public void parseLimit_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLimit((String) null));
    }

    @Test
    public void parseLimit_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLimit(INVALID_LIMIT));
    }

    @Test
    public void parseLimit_validValueWithoutWhitespace_returnsLimit() throws Exception {
        Limit expectedLimit = new Limit(VALID_LIMIT);
        assertEquals(expectedLimit, ParserUtil.parseLimit(VALID_LIMIT));
    }

    @Test
    public void parseLimit_validValueWithWhitespace_returnsTrimmedLimit() throws Exception {
        String limitWithWhitespace = WHITESPACE + VALID_LIMIT + WHITESPACE;
        Limit expectedLimit = new Limit(VALID_LIMIT);
        assertEquals(expectedLimit, ParserUtil.parseLimit(limitWithWhitespace));
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

package seedu.eylah.expensesplitter.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.diettracker.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;
import static seedu.eylah.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.model.item.ItemName;
import seedu.eylah.expensesplitter.model.person.Name;
import seedu.eylah.testutil.Assert;

public class ParserUtilTest {

    private static final String INVALID_NAME = "P3rm@s";
    private static final String INVALID_ITEM_NAME = "B33r T()W3R";

    private static final String VALID_NAME = "permas";
    private static final String VALID_ITEM_NAME = "Beer Tower";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
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
    public void parseItemName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseItemName((String) null));
    }

    @Test
    public void parseItemName_invalidItemName_throwsNullPointerException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseItemName(INVALID_ITEM_NAME));
    }

    @Test
    public void parseItemName_validItemNameWithoutWhiteSpace_throwsNullPointerException() throws Exception {
        ItemName itemName = new ItemName(VALID_ITEM_NAME);
        assertEquals(itemName, ParserUtil.parseItemName(VALID_ITEM_NAME));
    }








}

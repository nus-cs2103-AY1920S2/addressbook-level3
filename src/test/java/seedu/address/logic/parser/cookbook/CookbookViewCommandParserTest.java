package seedu.address.logic.parser.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cookbook.CookbookViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CookbookViewCommandParserTest {
    private static final Index VALID_INDEX = new Index(0);
    private static final String VALID_INDEX_STRING = "recipe 1";
    private static final String INVALID_INDEX_STRING = "Invalid index string";

    @Test
    public void parse_validInput() throws ParseException {
        CookbookViewCommandParser c = new CookbookViewCommandParser();
        assertEquals(c.parse(VALID_INDEX_STRING), new CookbookViewCommand(VALID_INDEX));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        CookbookViewCommandParser c = new CookbookViewCommandParser();
        assertThrows(NullPointerException.class, () -> c.parse(null));
    }

    @Test
    public void parse_invalidInput() {
        CookbookViewCommandParser c = new CookbookViewCommandParser();
        assertThrows(ParseException.class, () -> c.parse(INVALID_INDEX_STRING));
    }
}

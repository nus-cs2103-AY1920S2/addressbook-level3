package seedu.address.logic.parser.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cookbook.CookbookListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CookbookListCommandParserTest {
    private static final String VALID_STRING = "";
    private static final String INVALID_STRING = "invalid xyz";

    @Test
    public void parse_validInput() throws ParseException {
        CookbookListCommandParser c = new CookbookListCommandParser();
        assertEquals(c.parse(VALID_STRING), new CookbookListCommand());
    }

    @Test
    public void parse_invalidInput() {
        CookbookListCommandParser c = new CookbookListCommandParser();
        assertThrows(ParseException.class, () -> c.parse(INVALID_STRING));
    }
}

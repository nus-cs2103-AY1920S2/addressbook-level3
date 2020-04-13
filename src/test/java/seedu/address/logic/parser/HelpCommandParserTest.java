package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class HelpCommandParserTest {
    private static final String VALID_STRING = "";
    private static final String INVALID_STRING = "invalid xyz";

    @Test
    public void parse_validInput() throws ParseException {
        HelpCommandParser c = new HelpCommandParser();
        assertEquals(c.parse(VALID_STRING), new HelpCommand());
    }

    @Test
    public void parse_invalidInput() {
        HelpCommandParser c = new HelpCommandParser();
        assertThrows(ParseException.class, () -> c.parse(INVALID_STRING));
    }
}

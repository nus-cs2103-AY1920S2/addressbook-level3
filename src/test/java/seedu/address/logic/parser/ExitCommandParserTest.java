package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExitCommandParserTest {
    private static final String VALID_STRING = "";
    private static final String INVALID_STRING = "invalid xyz";

    @Test
    public void parse_validInput() throws ParseException {
        ExitCommandParser c = new ExitCommandParser();
        assertEquals(c.parse(VALID_STRING), new ExitCommand());
    }

    @Test
    public void parse_invalidInput() {
        ExitCommandParser c = new ExitCommandParser();
        assertThrows(ParseException.class, () -> c.parse(INVALID_STRING));
    }
}

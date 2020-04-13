package seedu.address.logic.parser.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cart.CartExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CartExportCommandParserTest {
    private static final String VALID_ARGUMENT = "";
    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        CartExportCommandParser c = new CartExportCommandParser();
        assertEquals(c.parse(VALID_ARGUMENT), new CartExportCommand());
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new CartExportCommandParser().parse(INVALID_ARGUMENT));
    }
}

package seedu.address.logic.parser.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cart.CartClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CartClearCommandParserTest {
    private static final String VALID_ARGUMENT = "";
    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        CartClearCommandParser c = new CartClearCommandParser();
        assertEquals(c.parse(VALID_ARGUMENT), new CartClearCommand());
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new CartClearCommandParser().parse(INVALID_ARGUMENT));
    }
}

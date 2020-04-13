package seedu.address.logic.parser.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cart.CartMoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CartMoveCommandParserTest {

    private static final String VALID_ARGUMENT = "";
    private static final String INVALID_ARGUMENT = "I'm here to play punk";

    @Test
    public void parse_validInput() throws ParseException {
        CartMoveCommandParser c = new CartMoveCommandParser();
        assertEquals(c.parse(VALID_ARGUMENT), new CartMoveCommand());
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new CartMoveCommandParser().parse(INVALID_ARGUMENT));
    }
}

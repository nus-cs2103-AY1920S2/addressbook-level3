package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.inventory.InventoryClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class InventoryClearCommandParserTest {
    private static final String VALID_ARGUMENT = "";
    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        InventoryClearCommandParser i = new InventoryClearCommandParser();
        assertEquals(i.parse(VALID_ARGUMENT), new InventoryClearCommand());
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new InventoryClearCommandParser().parse(INVALID_ARGUMENT));
    }
}

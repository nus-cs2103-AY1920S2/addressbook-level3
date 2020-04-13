package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.inventory.InventoryCookCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class InventoryCookCommandParserTest {
    private static final String VALID_ARGUMENT = "recipe 1";
    private static final String INVALID_ARGUMENT = "invalid 1";
    private static final String INVALID_ARGUMENT_INVALID_INDEX = "recipe 0";
    private static final Index VALID_INDEX = new Index(0);


    @Test
    public void parse_validInput() throws ParseException {
        InventoryCookCommandParser i = new InventoryCookCommandParser();
        assertEquals(i.parse(VALID_ARGUMENT), new InventoryCookCommand(VALID_INDEX));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new InventoryClearCommandParser().parse(INVALID_ARGUMENT));
        assertThrows(ParseException.class, () -> new InventoryClearCommandParser()
                .parse(INVALID_ARGUMENT_INVALID_INDEX));
    }
}

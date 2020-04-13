package seedu.address.logic.parser.inventory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.inventory.InventoryClearCommand;
import seedu.address.logic.commands.inventory.InventoryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses no input to return a InventoryClearCommand.
 */
public class InventoryClearCommandParser implements Parser<InventoryCommand> {

    /**
     * Parses no arguments in the context of the InventoryCommand
     * and returns a InventoryClearommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public InventoryClearCommand parse(String userInput) throws ParseException {
        if (userInput.isEmpty()) {
            return new InventoryClearCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InventoryClearCommand.MESSAGE_USAGE));
        }
    }
}

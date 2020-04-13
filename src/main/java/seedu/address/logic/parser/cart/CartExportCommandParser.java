package seedu.address.logic.parser.cart;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.cart.CartCommand;
import seedu.address.logic.commands.cart.CartExportCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CartExportCommand object
 */
public class CartExportCommandParser implements Parser<CartCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CartCommand
     * and returns a CartExportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public CartExportCommand parse(String userInput) throws ParseException {
        if (userInput.isEmpty()) {
            return new CartExportCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CartExportCommand.MESSAGE_USAGE));
        }
    }
}

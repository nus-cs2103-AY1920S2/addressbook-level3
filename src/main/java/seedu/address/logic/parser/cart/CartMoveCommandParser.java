package seedu.address.logic.parser.cart;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.cart.CartCommand;
import seedu.address.logic.commands.cart.CartMoveCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses no input to return a CartDoneCommand.
 */
public class CartMoveCommandParser implements Parser<CartCommand> {
    @Override
    public CartMoveCommand parse(String userInput) throws ParseException {
        if (userInput.isEmpty()) {
            return new CartMoveCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CartMoveCommand.MESSAGE_USAGE));
        }
    }
}

package seedu.address.logic.parser.cart;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.cart.CartAddCommand;
import seedu.address.logic.commands.cart.CartAddIngredientCommand;
import seedu.address.logic.commands.cart.CartAddRecipeIngredientCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CartAddCommand object
 */
public class CartAddCommandParser implements Parser<CartAddCommand> {

    private static final Pattern CART_ADD_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(" *(?<category>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the CartAddCommand
     * and returns a CartAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CartAddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final Matcher matcher = CART_ADD_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CartAddCommand.MESSAGE_USAGE));
        }

        final String category = matcher.group("category");
        final String arguments = matcher.group("arguments");

        switch (category) {
        case CartAddIngredientCommand.COMMAND_WORD:
            return new CartAddIngredientCommandParser().parse(arguments);
        case CartAddRecipeIngredientCommand.COMMAND_WORD:
            return new CartAddRecipeIngredientCommandParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CartAddCommand.MESSAGE_USAGE));
        }
    }
}

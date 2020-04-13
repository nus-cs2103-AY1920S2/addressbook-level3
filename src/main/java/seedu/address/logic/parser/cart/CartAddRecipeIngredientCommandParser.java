package seedu.address.logic.parser.cart;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.cart.CartAddCommand;
import seedu.address.logic.commands.cart.CartAddRecipeIngredientCommand;
import seedu.address.logic.commands.cart.CartCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CartAddRecipeIngredientCommand object
 */
public class CartAddRecipeIngredientCommandParser implements Parser<CartCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CartCommand
     * and returns a CartAddRecipeIngredientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CartAddRecipeIngredientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index recipeIndex;

        try {
            recipeIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    CartAddCommand.MESSAGE_USAGE), pe);
        }

        return new CartAddRecipeIngredientCommand(recipeIndex);
    }
}

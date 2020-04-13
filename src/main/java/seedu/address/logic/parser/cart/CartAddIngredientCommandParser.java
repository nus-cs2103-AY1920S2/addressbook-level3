package seedu.address.logic.parser.cart;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import seedu.address.logic.commands.cart.CartAddIngredientCommand;
import seedu.address.logic.commands.cart.CartCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * Parses input arguments and creates a new CartAddIngredientCommand object
 */
public class CartAddIngredientCommandParser implements Parser<CartCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CartCommand
     * and returns a CartAddIngredientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CartAddIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_QUANTITY);

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CartAddIngredientCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent();
        assert argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY).isPresent();
        IngredientName ingredientName = ParserUtil.parseIngredientName(argMultimap
                .getValue(PREFIX_INGREDIENT_NAME).get());
        IngredientQuantity ingredientQuantity = ParserUtil.parseIngredientQuantity(argMultimap
                .getValue(PREFIX_INGREDIENT_QUANTITY).get());
        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity);

        return new CartAddIngredientCommand(ingredient);
    }
}

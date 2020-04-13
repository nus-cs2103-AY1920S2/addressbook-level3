package seedu.address.logic.parser.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.inventory.InventoryAddIngredientCommand;
import seedu.address.logic.commands.inventory.InventoryCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * Parses input arguments and creates a new InventoryAddIngredientCommand object
 */
public class InventoryAddIngredientCommandParser implements Parser<InventoryCommand> {

    private static final Pattern INVENTORY_ADD_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(InventoryAddIngredientCommand.INGREDIENT_KEYWORD + "*(?<arguments>.*)");


    /**
     * Parses the given {@code String} of arguments in the context of the InventoryCommand
     * and returns a InventoryAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InventoryAddIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final Matcher matcher = INVENTORY_ADD_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InventoryAddIngredientCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_QUANTITY);

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InventoryAddIngredientCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME);
        assert argMultimap.getPreamble().isEmpty();
        assert argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent();
        assert argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY).isPresent();
        IngredientName ingredientName = ParserUtil.parseIngredientName(argMultimap
                .getValue(PREFIX_INGREDIENT_NAME).get());
        IngredientQuantity ingredientQuantity = ParserUtil.parseIngredientQuantity(argMultimap
                .getValue(PREFIX_INGREDIENT_QUANTITY).get());
        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity);

        return new InventoryAddIngredientCommand(ingredient);
    }
}

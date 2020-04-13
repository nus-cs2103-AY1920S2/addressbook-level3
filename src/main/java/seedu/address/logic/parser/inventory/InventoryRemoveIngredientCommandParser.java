package seedu.address.logic.parser.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.inventory.InventoryCommand;
import seedu.address.logic.commands.inventory.InventoryRemoveIngredientCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * Parses input arguments and creates a new InventoryRemoveIngredientCommand object
 */
public class InventoryRemoveIngredientCommandParser implements Parser<InventoryCommand> {

    private static final Pattern INVENTORY_REMOVE_COMMAND_ARGUMENT_FORMAT = Pattern
            .compile(InventoryRemoveIngredientCommand.INGREDIENT_KEYWORD + "*(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the InventoryCommand
     * and returns a InventoryRemoveIngredientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InventoryRemoveIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final Matcher matcher = INVENTORY_REMOVE_COMMAND_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InventoryRemoveIngredientCommand.MESSAGE_USAGE));
        }

        final String arguments = matcher.group("arguments");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_QUANTITY);

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InventoryRemoveIngredientCommand.MESSAGE_USAGE));
        }

        assert argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME);
        assert argMultimap.getPreamble().isEmpty();
        assert argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent();
        IngredientName ingredientName = ParserUtil.parseIngredientName(argMultimap
                .getValue(PREFIX_INGREDIENT_NAME).get());

        Optional<IngredientQuantity> ingredientQuantity = argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_QUANTITY)
                ? Optional.of(ParserUtil.parseIngredientQuantity(argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY)
                    .get()))
                : Optional.empty();

        return new InventoryRemoveIngredientCommand(ingredientName, ingredientQuantity);
    }
}

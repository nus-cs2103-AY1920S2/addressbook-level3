package team.easytravel.logic.parser.packinglist;

import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import team.easytravel.logic.commands.packinglist.AddItemCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.Prefix;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.listmanagers.packinglistitem.ItemCategory;
import team.easytravel.model.listmanagers.packinglistitem.ItemName;
import team.easytravel.model.listmanagers.packinglistitem.PackingListItem;
import team.easytravel.model.listmanagers.packinglistitem.Quantity;

/**
 * The type Add item parser.
 */
public class AddItemCommandParser implements Parser<AddItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddItemCommand
     * and returns an AddItemCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM_NAME, PREFIX_QUANTITY, PREFIX_ITEM_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_ITEM_NAME, PREFIX_QUANTITY, PREFIX_ITEM_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddItemCommand.MESSAGE_USAGE));
        }

        ItemName name = ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM_NAME).get());
        Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        ItemCategory category = ParserUtil.parseItemCategory(argMultimap.getValue(PREFIX_ITEM_CATEGORY).get());

        PackingListItem packingListItem = new PackingListItem(name, quantity, category, false);

        return new AddItemCommand(packingListItem);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

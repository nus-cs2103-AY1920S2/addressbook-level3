package team.easytravel.logic.parser.packinglist;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_QUANTITY;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.packinglist.EditItemCommand;
import team.easytravel.logic.commands.packinglist.EditItemCommand.EditItemDescriptor;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * The type Edit item parser.
 */
public class EditItemCommandParser implements Parser<EditItemCommand> {
    @Override
    public EditItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ITEM_NAME, PREFIX_QUANTITY, PREFIX_ITEM_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditItemCommand.MESSAGE_USAGE), pe);
        }

        EditItemDescriptor editItemDescriptor = new EditItemDescriptor();
        if (argMultimap.getValue(PREFIX_ITEM_NAME).isPresent()) {
            editItemDescriptor.setItemName(ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editItemDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_ITEM_CATEGORY).isPresent()) {
            editItemDescriptor.setItemCategory(ParserUtil.parseItemCategory
                    (argMultimap.getValue(PREFIX_ITEM_CATEGORY).get()));
        }

        if (!editItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditItemCommand.MESSAGE_NOT_EDITED);
        }

        return new EditItemCommand(index, editItemDescriptor);
    }
}

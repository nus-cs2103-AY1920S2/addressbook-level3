package seedu.address.logic.parser.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.customer.EditCustomerCommand;
import seedu.address.logic.commands.product.EditProductCommand;
import seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditProductCommandParser implements Parser<EditProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditProductCommand
     * and returns an EditProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditProductCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_COSTPRICE, PREFIX_PRICE,
                        PREFIX_QUANTITY, PREFIX_SALES);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    EditProductCommand.MESSAGE_USAGE), pe);
        }

        EditProductDescriptor editProductDescriptor = getEditProductDescriptor(argMultimap);

        if (!editProductDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditProductCommand.MESSAGE_NOT_EDITED);
        }

        return new EditProductCommand(index, editProductDescriptor);
    }

    /**
     * Get an edit product descriptor from the user input values.
     * @param argMultimap
     * @return edit product descriptor
     * @throws ParseException
     */
    private EditProductDescriptor getEditProductDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        EditProductDescriptor editProductDescriptor = new EditProductDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editProductDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_COSTPRICE).isPresent()) {
            editProductDescriptor.setCostPrice(ParserUtil
                    .parseCostPrice(argMultimap.getValue(PREFIX_COSTPRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editProductDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editProductDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }
        if (argMultimap.getValue(PREFIX_SALES).isPresent()) {
            editProductDescriptor.setSales(ParserUtil.parseMoney(argMultimap.getValue(PREFIX_SALES).get()));
        }
        return editProductDescriptor;
    }
}

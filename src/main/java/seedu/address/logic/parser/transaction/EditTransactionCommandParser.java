package seedu.address.logic.parser.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANS_DESCRIPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.transaction.EditTransactionCommand;
import seedu.address.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditTransactionCommandParser implements Parser<EditTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTransactionCommand
     * and returns an EditTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMER, PREFIX_PRODUCT, PREFIX_DATETIME,
                        PREFIX_QUANTITY, PREFIX_MONEY, PREFIX_TRANS_DESCRIPTION);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTransactionCommand.MESSAGE_USAGE), pe);
        }

        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();
        if (argMultimap.getValue(PREFIX_CUSTOMER).isPresent()) {
            editTransactionDescriptor.setCustomerIndex(
                    ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CUSTOMER).get())
            );
        }
        if (argMultimap.getValue(PREFIX_PRODUCT).isPresent()) {
            editTransactionDescriptor.setProductIndex(
                    ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PRODUCT).get())
            );
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editTransactionDescriptor.setDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get())
            );
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            editTransactionDescriptor.setQuantity(
                    ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get())
            );
        }
        if (argMultimap.getValue(PREFIX_MONEY).isPresent()) {
            editTransactionDescriptor.setMoney(
                    ParserUtil.parseMoney(argMultimap.getValue(PREFIX_MONEY).get())
            );
        }
        if (argMultimap.getValue(PREFIX_TRANS_DESCRIPTION).isPresent()) {
            editTransactionDescriptor.setDescription(
                    ParserUtil.parseTransDescription(argMultimap.getValue(PREFIX_TRANS_DESCRIPTION).get()));
        }

        if (!editTransactionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTransactionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTransactionCommand(index, editTransactionDescriptor);
    }
}


package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditOrderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand Object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS,
                        PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COD, PREFIX_COMMENT, PREFIX_TYPE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        if (argMultimap.getValue(PREFIX_TID).isPresent()) {
            editOrderDescriptor.setTid(ParserUtil.parseTid(argMultimap.getValue(PREFIX_TID).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editOrderDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editOrderDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editOrderDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent()) {
            editOrderDescriptor.setTimeStamp(
                    ParserUtil.parseTimeStamp(argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).get()));
        }
        if (argMultimap.getValue(PREFIX_WAREHOUSE).isPresent()) {
            editOrderDescriptor.setWarehouse(
                    ParserUtil.parseWarehouse(argMultimap.getValue(PREFIX_WAREHOUSE).get()));
        }
        if (argMultimap.getValue(PREFIX_COD).isPresent()) {
            editOrderDescriptor.setCash(ParserUtil.parseCash(argMultimap.getValue(PREFIX_COD).get()));
        }
        if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            editOrderDescriptor.setComment(ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).get()));
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editOrderDescriptor.setItemType(ParserUtil.parseItemType(argMultimap.getValue(PREFIX_TYPE).get()));
        }
        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editOrderDescriptor);
    }
}

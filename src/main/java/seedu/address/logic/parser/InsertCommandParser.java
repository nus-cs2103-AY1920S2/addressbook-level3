package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.stream.Stream;

import seedu.address.logic.commands.InsertCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.parcel.comment.Comment;
import seedu.address.model.parcel.itemtype.TypeOfItem;
import seedu.address.model.parcel.order.CashOnDelivery;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.parcelattributes.Address;
import seedu.address.model.parcel.parcelattributes.Email;
import seedu.address.model.parcel.parcelattributes.Name;
import seedu.address.model.parcel.parcelattributes.Phone;
import seedu.address.model.parcel.parcelattributes.TimeStamp;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.parcelattributes.Warehouse;

/**
 * Parses input arguments and creates a new InsertCommand object
 */
public class InsertCommandParser implements Parser<InsertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InsertCommand
     * and returns an InsertCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InsertCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COD, PREFIX_TYPE,
                        PREFIX_COMMENT);
        if (!arePrefixesPresent(argMultimap, PREFIX_TID, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertCommand.MESSAGE_USAGE));
        }

        TransactionId tid = ParserUtil.parseTid(argMultimap.getValue(PREFIX_TID).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        TimeStamp timeStamp = ParserUtil.parseTimeStamp(argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).get());
        Warehouse warehouse = ParserUtil.parseWarehouse(argMultimap.getValue(PREFIX_WAREHOUSE).get());
        CashOnDelivery cash = ParserUtil.parseCash(argMultimap.getValue(PREFIX_COD).get());
        Comment comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).isEmpty()
                ? "NIL"
                : argMultimap.getValue(PREFIX_COMMENT).get());
        TypeOfItem type = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_TYPE).isEmpty()
                ? "NIL"
                : argMultimap.getValue(PREFIX_TYPE).get());

        Order order = new Order(tid, name, phone, email, address, timeStamp, warehouse, cash, comment, type);

        return new InsertCommand(order);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

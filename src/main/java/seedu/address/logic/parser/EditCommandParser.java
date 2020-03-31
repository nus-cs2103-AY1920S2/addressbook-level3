package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditParcelDescriptor;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand Object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Flag flag = null;

        if (areFlagsPresent(args)) {
            flag = extractFlag(args);
            args = removeFlags(args);
        } else {
            throw new ParseException(MESSAGE_MISSING_FLAG);
        }

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DELIVERY_TIMESTAMP, PREFIX_RETURN_TIMESTAMP, PREFIX_WAREHOUSE,
                PREFIX_COD, PREFIX_COMMENT, PREFIX_TYPE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditParcelDescriptor editParcelDescriptor = new EditParcelDescriptor();
        if (argMultimap.getValue(PREFIX_TID).isPresent()) {
            editParcelDescriptor.setTid(ParserUtil.parseTid(argMultimap.getValue(PREFIX_TID).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editParcelDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editParcelDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editParcelDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editParcelDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent() && flag.equals(CliSyntax.FLAG_ORDER_BOOK)) {
            editParcelDescriptor.setTimeStamp(
                ParserUtil.parseTimeStamp(argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).get()));
        } else if (argMultimap.getValue(PREFIX_RETURN_TIMESTAMP).isPresent()
            && flag.equals(CliSyntax.FLAG_RETURN_BOOK)) {
            editParcelDescriptor.setTimeStamp(
                ParserUtil.parseTimeStamp(argMultimap.getValue(PREFIX_RETURN_TIMESTAMP).get()));
        } else if (
            (argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent() && flag.equals(CliSyntax.FLAG_RETURN_BOOK))
            || (argMultimap.getValue(PREFIX_RETURN_TIMESTAMP).isPresent() && flag.equals(CliSyntax.FLAG_ORDER_BOOK))
            || (argMultimap.getValue(PREFIX_RETURN_TIMESTAMP).isPresent()
                && argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent())){
            throw new ParseException(MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP);
        }
        if (argMultimap.getValue(PREFIX_WAREHOUSE).isPresent()) {
            editParcelDescriptor.setWarehouse(
                ParserUtil.parseWarehouse(argMultimap.getValue(PREFIX_WAREHOUSE).get()));
        }
        if (argMultimap.getValue(PREFIX_COD).isPresent() && flag.equals(CliSyntax.FLAG_ORDER_BOOK)) {
            editParcelDescriptor.setCash(ParserUtil.parseCash(argMultimap.getValue(PREFIX_COD).get()));
        }
        if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            editParcelDescriptor.setComment(ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).get()));
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editParcelDescriptor.setItemType(ParserUtil.parseItemType(argMultimap.getValue(PREFIX_TYPE).get()));
        }
        if (!editParcelDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editParcelDescriptor, flag);
    }

    /**
     * Checks for presence of any flag given by user, return true if there is any.
     *
     * @param string String object representing the user input.
     * @return Return boolean value representing the presence of any flags.
     */
    private boolean areFlagsPresent(String string) {
        List<String> listOfStr = Arrays.asList(string.split("\\s"));
        return Stream
            .of(CliSyntax.FLAG_RETURN_BOOK, CliSyntax.FLAG_ORDER_BOOK)
            .anyMatch(flag -> listOfStr.contains(flag.getFlag()));
    }

    /**
     * Returns a {@code Flag} object according to the flag user provided in.
     *
     * @param arg String object representing the user input.
     * @return {@code Flag} object representing the flag given.
     * @throws ParseException ParseException is thrown when multiple different flags are detected.
     */
    private Flag extractFlag(String arg) throws ParseException {
        List<String> argArr = Arrays.asList(arg.trim().split("\\s"));
        if (argArr.contains(CliSyntax.FLAG_ORDER_BOOK.getFlag())
            && argArr.contains(CliSyntax.FLAG_RETURN_BOOK.getFlag())) {
            throw new ParseException(SearchCommand.MULTIPLE_FLAGS_DETECTED);
        }

        if (argArr.contains(CliSyntax.FLAG_RETURN_BOOK.getFlag())) {
            return CliSyntax.FLAG_RETURN_BOOK;
        } else {
            return CliSyntax.FLAG_ORDER_BOOK;
        }
    }

    /**
     * Returns a String object with all flags removed from it.
     *
     * @param args String object representing the input given by the user.
     * @return a String object with all flags removed from it.
     */
    private String removeFlags(String args) {
        List<String> listOfArgs = Arrays.asList(args.split("\\s"));
        String returnString = listOfArgs
            .stream()
            .filter(each -> Stream
                .of(CliSyntax.FLAG_RETURN_BOOK, CliSyntax.FLAG_ORDER_BOOK)
                .noneMatch(flag -> each.equals(flag.getFlag())))
            .map(each -> each + " ")
            .collect(Collectors.joining());
        return returnString;
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.Collections;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditPersonDescriptor;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        Index index;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK,
                        PREFIX_BIRTHDAY, PREFIX_ORGANIZATION, PREFIX_DELETE_TAG);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent() || argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_PREFIX, DeleteCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(""));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(""));
        }
        if (argMultimap.getValue(PREFIX_BIRTHDAY).isPresent()) {
            editPersonDescriptor.setBirthday(ParserUtil.parseBirthday(""));
        }
        if (argMultimap.getValue(PREFIX_ORGANIZATION).isPresent()) {
            editPersonDescriptor.setOrganization(ParserUtil.parseOrganization(""));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editPersonDescriptor.setRemarks(ParserUtil.parseRemarks(Collections.emptyList()));
        }
        if (argMultimap.getValue(PREFIX_DELETE_TAG).isPresent()) {
            editPersonDescriptor.setTagsToEmpty(ParserUtil.parseTags(Collections.emptyList()));
        }

        return new DeleteCommand(index, editPersonDescriptor);
    }
}

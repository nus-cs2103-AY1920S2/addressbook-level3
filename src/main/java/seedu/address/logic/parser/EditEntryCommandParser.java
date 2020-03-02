package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRYCALORIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRYLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRYNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRYTIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditEntryCommandParser implements Parser<EditEntryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEntryCommand
     * and returns an EditEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEntryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRYNAME, PREFIX_ENTRYTIME, PREFIX_ENTRYLOCATION,
                        PREFIX_ENTRYCALORIE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE), pe);
        }

        EditEntryDescriptor editEntryDescriptor = new EditEntryDescriptor();
        if (argMultimap.getValue(PREFIX_ENTRYNAME).isPresent()) {
            editEntryDescriptor.setName(argMultimap.getValue(PREFIX_ENTRYNAME).get());
        }
        if (argMultimap.getValue(PREFIX_ENTRYTIME).isPresent()) {
            editEntryDescriptor.setTime(argMultimap.getValue(PREFIX_ENTRYTIME).get());
        }
        if (argMultimap.getValue(PREFIX_ENTRYLOCATION).isPresent()) {
            editEntryDescriptor.setLocation(argMultimap.getValue(PREFIX_ENTRYLOCATION).get());
        }
        if (argMultimap.getValue(PREFIX_ENTRYCALORIE).isPresent()) {
            editEntryDescriptor.setCalorie(argMultimap.getValue(PREFIX_ENTRYCALORIE).get());
        }

        if (!editEntryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEntryCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEntryCommand(index, editEntryDescriptor);
    }

}


package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditEventDescriptor;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parsers input commands and creates a new EditEventCommand.
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENTTITLE, PREFIX_EVENTDATE, PREFIX_DURATION, PREFIX_PLACE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE), pe);
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        if (argMultimap.getValue(PREFIX_EVENTTITLE).isPresent()) {
            editEventDescriptor.setEventTitle(ParserUtil.parseEventTitle(
                    argMultimap.getValue(PREFIX_EVENTTITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENTDATE).isPresent()) {
            editEventDescriptor.setEventDate(ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENTDATE).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editEventDescriptor.setDuration(ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }
        if (argMultimap.getValue(PREFIX_PLACE).isPresent()) {
            editEventDescriptor.setPlace(ParserUtil.parsePlace(argMultimap.getValue(PREFIX_PLACE).get()));
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);

    }
}

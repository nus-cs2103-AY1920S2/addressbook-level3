package team.easytravel.logic.parser.transportbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_END_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_MODE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_START_LOCATION;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.transportbooking.EditTransportBookingCommand;
import team.easytravel.logic.commands.transportbooking.EditTransportBookingCommand.EditTransportBookingDescriptor;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTransportBookingCommand object
 */
public class EditTransportBookingCommandParser implements Parser<EditTransportBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTransportBookingCommand
     * and returns an EditTransportBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTransportBookingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODE, PREFIX_START_LOCATION, PREFIX_END_LOCATION,
                        PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT,
                    "transport booking"), pe);
        }

        EditTransportBookingDescriptor editTransportBookingDescriptor = new EditTransportBookingDescriptor();
        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            editTransportBookingDescriptor.setMode(ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get()));
        }
        if (argMultimap.getValue(PREFIX_START_LOCATION).isPresent()) {
            editTransportBookingDescriptor.setStartLocation(
                    ParserUtil.parseLocation(argMultimap.getValue(PREFIX_START_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_END_LOCATION).isPresent()) {
            editTransportBookingDescriptor.setEndLocation(
                    ParserUtil.parseLocation(argMultimap.getValue(PREFIX_END_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATE_TIME).isPresent()) {
            editTransportBookingDescriptor.setStartDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE_TIME).isPresent()) {
            editTransportBookingDescriptor.setEndDateTime(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME).get()));
        }

        if (!editTransportBookingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTransportBookingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTransportBookingCommand(index, editTransportBookingDescriptor);
    }
}

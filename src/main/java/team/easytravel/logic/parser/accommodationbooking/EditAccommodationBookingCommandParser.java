package team.easytravel.logic.parser.accommodationbooking;

import static java.util.Objects.requireNonNull;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.accommodationbooking.EditAccommodationBookingCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.CliSyntax;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAccommodationBookingCommand object
 */
public class EditAccommodationBookingCommandParser implements Parser<EditAccommodationBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAccommodationBookingCommand
     * and returns an EditAccommodationBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAccommodationBookingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_ACCOMMODATION_NAME,
                        CliSyntax.PREFIX_ACCOMMODATION_LOCATION,
                        CliSyntax.PREFIX_ACCOMMODATION_START_DAY,
                        CliSyntax.PREFIX_ACCOMMODATION_END_DAY,
                        CliSyntax.PREFIX_ACCOMMODATION_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAccommodationBookingCommand.MESSAGE_USAGE), pe);
        }

        EditAccommodationBookingCommand.EditAccommodationBookingDescriptor editAccommodationBookingDescriptor =
                new EditAccommodationBookingCommand.EditAccommodationBookingDescriptor();

        if (argMultimap.getValue(CliSyntax.PREFIX_ACCOMMODATION_NAME).isPresent()) {
            editAccommodationBookingDescriptor.setAccommodationName(ParserUtil.parseAccommodationName(argMultimap
                    .getValue(CliSyntax.PREFIX_ACCOMMODATION_NAME).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_ACCOMMODATION_LOCATION).isPresent()) {
            editAccommodationBookingDescriptor.setLocation(ParserUtil.parseLocation(argMultimap
                    .getValue(CliSyntax.PREFIX_ACCOMMODATION_LOCATION).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_ACCOMMODATION_START_DAY).isPresent()) {
            editAccommodationBookingDescriptor.setStartDay(ParserUtil.parseDay(argMultimap
                    .getValue(CliSyntax.PREFIX_ACCOMMODATION_START_DAY).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_ACCOMMODATION_END_DAY).isPresent()) {
            editAccommodationBookingDescriptor.setEndDay(ParserUtil.parseDay(argMultimap
                    .getValue(CliSyntax.PREFIX_ACCOMMODATION_END_DAY).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_ACCOMMODATION_REMARK).isPresent()) {
            editAccommodationBookingDescriptor.setRemark(ParserUtil.parseRemark(argMultimap
                    .getValue(CliSyntax.PREFIX_ACCOMMODATION_REMARK).get()));
        }

        if (!editAccommodationBookingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAccommodationBookingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAccommodationBookingCommand(index, editAccommodationBookingDescriptor);
    }

}

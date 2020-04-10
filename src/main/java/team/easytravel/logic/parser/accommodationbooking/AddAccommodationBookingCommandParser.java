package team.easytravel.logic.parser.accommodationbooking;

import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_END_DAY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_NAME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_REMARK;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_START_DAY;

import java.util.stream.Stream;

import team.easytravel.logic.commands.accommodationbooking.AddAccommodationBookingCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.Prefix;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationName;
import team.easytravel.model.listmanagers.accommodationbooking.Day;
import team.easytravel.model.listmanagers.accommodationbooking.Remark;
import team.easytravel.model.util.attributes.Location;

/**
 * Parses input arguments and creates a new AddAccommodationBooking object
 */
public class AddAccommodationBookingCommandParser implements Parser<AddAccommodationBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAccommodationBookingCommand
     * and returns an AddAccommodationBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAccommodationBookingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACCOMMODATION_NAME, PREFIX_ACCOMMODATION_LOCATION,
                        PREFIX_ACCOMMODATION_START_DAY, PREFIX_ACCOMMODATION_END_DAY, PREFIX_ACCOMMODATION_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_ACCOMMODATION_NAME, PREFIX_ACCOMMODATION_LOCATION,
                PREFIX_ACCOMMODATION_START_DAY, PREFIX_ACCOMMODATION_END_DAY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAccommodationBookingCommand.MESSAGE_USAGE));
        }

        AccommodationName accommodationName = ParserUtil.parseAccommodationName(argMultimap
                .getValue(PREFIX_ACCOMMODATION_NAME).get());
        Location accommodationLocation = ParserUtil.parseLocation(argMultimap
                .getValue(PREFIX_ACCOMMODATION_LOCATION).get());
        Day startDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_ACCOMMODATION_START_DAY).get());
        Day endDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_ACCOMMODATION_END_DAY).get());

        Remark remark;
        if (argMultimap.getValue(PREFIX_ACCOMMODATION_REMARK).isPresent()) {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_ACCOMMODATION_REMARK).get());
        } else {
            remark = new Remark(" ");
        }

        try {
            AccommodationBooking accommodationBooking = new AccommodationBooking(accommodationName,
                    accommodationLocation, startDay, endDay, remark);
            return new AddAccommodationBookingCommand(accommodationBooking);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

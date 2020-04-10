package team.easytravel.logic.parser.trip;

import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_BUDGET;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_END_DATE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_EXCHANGE_RATE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_START_DATE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_TITLE;

import java.util.stream.Stream;

import team.easytravel.commons.core.time.Date;
import team.easytravel.logic.commands.trip.SetTripCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.Prefix;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.trip.Budget;
import team.easytravel.model.trip.ExchangeRate;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.util.attributes.Title;

/**
 * Parses input arguments and creates a new SetTripCommand object
 */
public class SetTripCommandParser implements Parser<SetTripCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetTripCommand
     * and returns an SetTripCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetTripCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TRIP_TITLE, PREFIX_TRIP_BUDGET, PREFIX_TRIP_EXCHANGE_RATE,
                        PREFIX_TRIP_START_DATE, PREFIX_TRIP_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TRIP_TITLE, PREFIX_TRIP_BUDGET, PREFIX_TRIP_EXCHANGE_RATE,
                PREFIX_TRIP_START_DATE, PREFIX_TRIP_END_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetTripCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TRIP_TITLE).get());
        Budget budget = ParserUtil.parseBudget(argMultimap.getValue(PREFIX_TRIP_BUDGET).get());
        Date startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_TRIP_START_DATE).get());
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_TRIP_END_DATE).get());
        ExchangeRate exchangeRate = ParserUtil.parseExchangeRate(argMultimap.getValue(PREFIX_TRIP_EXCHANGE_RATE).get());

        try {
            Trip trip = new Trip(title, startDate, endDate, budget, exchangeRate);
            return new SetTripCommand(trip);
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

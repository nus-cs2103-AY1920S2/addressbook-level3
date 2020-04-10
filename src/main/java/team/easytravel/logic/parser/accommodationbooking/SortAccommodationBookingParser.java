package team.easytravel.logic.parser.accommodationbooking;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.accommodationbooking.SortAccommodationBookingCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a SortFixedExpenseCommand.
 */
public class SortAccommodationBookingParser implements Parser<SortAccommodationBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortFixedExpenseCommand
     * and returns an SortFixedExpenseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortAccommodationBookingCommand parse(String args) throws ParseException {
        try {
            if (args.length() < 1) { //The case where nothing is placed after sortacc
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        SortAccommodationBookingCommand.MESSAGE_USAGE));
            }

            String[] sortIdentifiers = ParserUtil.parseSortArgumentString(args);

            String parseSortIdentifier = ParserUtil.parseSortIdentifier(sortIdentifiers[0].toLowerCase());
            String parseSortParameter = ParserUtil.parseSortExpParameters(sortIdentifiers[1].toLowerCase());


            return new SortAccommodationBookingCommand((parseSortIdentifier), parseSortParameter);
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SortAccommodationBookingCommand.MESSAGE_USAGE), pe);
        }
    }

}

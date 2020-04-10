package team.easytravel.logic.parser.accommodationbooking;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.accommodationbooking.DeleteAccommodationBookingCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAccommodationBookingCommand object.
 */
public class DeleteAccommodationBookingCommandParser implements Parser<DeleteAccommodationBookingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAccommodationBookingCommand
     * and returns a DeleteAccommodationBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAccommodationBookingCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeleteAccommodationBookingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteAccommodationBookingCommand.MESSAGE_USAGE), pe);
        }
    }

}

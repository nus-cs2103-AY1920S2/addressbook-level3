package team.easytravel.logic.parser.fixedexpense;

import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.accommodationbooking.DeleteAccommodationBookingCommand;
import team.easytravel.logic.commands.fixedexpense.DeleteFixedExpenseCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFixedExpenseCommand.
 */
public class DeleteFixedExpenseCommandParser implements Parser<DeleteFixedExpenseCommand> {

    @Override
    public DeleteFixedExpenseCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeleteFixedExpenseCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAccommodationBookingCommand.MESSAGE_USAGE), pe);
        }
    }
}

package team.easytravel.logic.parser.activity;

import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.activity.DeleteActivityCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteActivityCommand.
 */
public class DeleteActivityCommandParser implements Parser<DeleteActivityCommand> {

    @Override
    public DeleteActivityCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeleteActivityCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteActivityCommand.MESSAGE_USAGE), pe);
        }
    }
}

package team.easytravel.logic.parser.trip;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.trip.RenameCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.util.attributes.Title;

/**
 * Parses input arguments and creates a new RenameCommand object
 */
public class RenameCommandParser implements Parser<RenameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenameCommand
     * and returns an RenameCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        if (!Title.isValidTitle(trimmedArg)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    RenameCommand.MESSAGE_USAGE));
        }

        try {
            Title editTitle = new Title(trimmedArg);
            return new RenameCommand(editTitle);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

}

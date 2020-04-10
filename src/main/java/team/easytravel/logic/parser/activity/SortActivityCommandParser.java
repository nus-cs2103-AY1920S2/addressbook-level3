package team.easytravel.logic.parser.activity;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.activity.SortActivityCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a SortActivityCommand.
 */
public class SortActivityCommandParser implements Parser<SortActivityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortActivityCommand
     * and returns an SortActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortActivityCommand parse(String args) throws ParseException {
        try {
            if (args.length() < 1) { //The case where nothing is placed after sortActivity
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        SortActivityCommand.MESSAGE_USAGE));
            }
            String[] sortIdentifiers = ParserUtil.parseSortArgumentString(args);
            String parseSortIdentifier = ParserUtil.parseSortIdentifier(sortIdentifiers[0].toLowerCase());
            String parseSortParameter = ParserUtil.parseSortActivityParameters(sortIdentifiers[1].toLowerCase());

            return new SortActivityCommand((parseSortIdentifier), parseSortParameter);
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SortActivityCommand.MESSAGE_USAGE), pe);
        }
    }

}

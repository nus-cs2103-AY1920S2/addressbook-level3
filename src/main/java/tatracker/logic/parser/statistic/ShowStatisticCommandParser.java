package tatracker.logic.parser.statistic;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import tatracker.logic.commands.FindCommand;
import tatracker.logic.commands.statistic.ShowStatisticCommand;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowStatisticCommand object
 */
public class ShowStatisticCommandParser implements Parser<ShowStatisticCommand> {

    public ShowStatisticCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ShowStatisticCommand(null);
        }

        return new ShowStatisticCommand(trimmedArgs);
    }

}

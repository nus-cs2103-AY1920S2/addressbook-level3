//@@author Eclmist

package tatracker.logic.parser.statistic;

import tatracker.logic.commands.statistic.ShowStatisticCommand;
import tatracker.logic.parser.Parser;

/**
 * Parses input arguments and creates a new ShowStatisticCommand object
 */
public class ShowStatisticCommandParser implements Parser<ShowStatisticCommand> {

    /**
     * Parse the user input for command execution.
     *
     * @param args The input arguments
     * @return a ShowStatisticCommand object
     */
    public ShowStatisticCommand parse(String args) {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ShowStatisticCommand();
        }

        return new ShowStatisticCommand(trimmedArgs);
    }
}

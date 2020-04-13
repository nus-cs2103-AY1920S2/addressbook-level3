package seedu.nova.logic.parser.plannerparser;

import seedu.nova.logic.commands.plannercommands.PlannerStatsCommand;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * List command parser
 */
public class StatsCommandParser implements Parser<PlannerStatsCommand> {

    @Override
    public PlannerStatsCommand parse(String args) throws ParseException {
        return new PlannerStatsCommand();
    }
}

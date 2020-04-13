// @@author Eclmist

package tatracker.logic.commands.statistic;

import tatracker.logic.commands.CommandResult;

/**
 * Shows the statistics window for the selected module.
 */
public class StatisticCommandResult extends CommandResult {

    public final String targetModuleCode;

    public StatisticCommandResult(String feedbackToUser, String moduleCode) {
        super(feedbackToUser, Action.NONE);
        targetModuleCode = moduleCode;
    }
}

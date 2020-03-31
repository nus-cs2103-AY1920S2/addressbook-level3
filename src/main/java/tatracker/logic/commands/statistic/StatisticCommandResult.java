package tatracker.logic.commands.statistic;

import tatracker.logic.commands.CommandResult;

public class StatisticCommandResult extends CommandResult {

    public final String targetModuleCode;

    public StatisticCommandResult(String feedbackToUser, String moduleCode) {
        super(feedbackToUser, false, false);
        targetModuleCode = moduleCode;
    }
}

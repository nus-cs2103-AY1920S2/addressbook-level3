package tatracker.logic.commands.statistic;

import static java.util.Objects.requireNonNull;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Format full help instructions for every command for display.
 */
public class ShowStatisticCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows statistics report of a particular module.\n"
            + "Example: " + COMMAND_WORD + " CS2101";

    public static final String SHOWING_REPORT_MESSAGE = "Opened Statistic Window.";
    public static final String INVALID_MODULE_CODE_ERROR = "Target module not found!";

    public final String targetModuleCode;

    public ShowStatisticCommand(String moduleCode) {
        this.targetModuleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetModuleCode == null) {
            return new StatisticCommandResult(SHOWING_REPORT_MESSAGE, null);
        }

        if (model.hasModule(new Module(targetModuleCode))) {
            return new StatisticCommandResult(SHOWING_REPORT_MESSAGE, targetModuleCode);
        }

        throw new CommandException(INVALID_MODULE_CODE_ERROR);
    }
}

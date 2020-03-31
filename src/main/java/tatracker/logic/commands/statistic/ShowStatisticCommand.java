package tatracker.logic.commands.statistic;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.MODULE_ID;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.module.Module;

/**
 * Format full help instructions for every command for display.
 */
public class ShowStatisticCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.REPORT,
            "Shows the statistics report of a particular module.",
            List.of(MODULE_ID),
            List.of(),
            MODULE_ID
    );

    public static final String MESSAGE_OPENED_STATS = "Opened Statistic Window.";
    public static final String MESSAGE_INVALID_MODULE = "Target module not found!";

    public final String targetModuleCode;

    public ShowStatisticCommand(String moduleCode) {
        this.targetModuleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetModuleCode == null) {
            return new StatisticCommandResult(MESSAGE_OPENED_STATS, null);
        }

        if (model.hasModule(new Module(targetModuleCode))) {
            return new StatisticCommandResult(MESSAGE_OPENED_STATS, targetModuleCode);
        }

        throw new CommandException(MESSAGE_INVALID_MODULE);
    }
}

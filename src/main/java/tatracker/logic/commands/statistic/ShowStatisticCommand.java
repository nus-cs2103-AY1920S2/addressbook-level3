// @@author Eclmist

package tatracker.logic.commands.statistic;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.parser.Prefixes.MODULE_ID;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class ShowStatisticCommand extends Command {

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.REPORT,
            "Shows the statistics report of a particular module",
            List.of(MODULE_ID),
            List.of(),
            MODULE_ID
    );

    // @@author Eclmist

    public static final String MESSAGE_OPENED_STATS = "Opened statistic window";

    private final String module;

    public ShowStatisticCommand() {
        this(null);
    }

    public ShowStatisticCommand(String module) {
        this.module = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (module == null) {
            return new StatisticCommandResult(MESSAGE_OPENED_STATS, null);
        }

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        return new StatisticCommandResult(MESSAGE_OPENED_STATS, module);
    }
}

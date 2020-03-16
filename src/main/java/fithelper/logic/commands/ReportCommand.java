package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.REPORT;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays weekly report page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Weekly Report Page ~";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS), REPORT, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReportCommand);
    }
}

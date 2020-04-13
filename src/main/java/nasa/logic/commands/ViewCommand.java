package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import nasa.model.Model;
import nasa.model.View;

/* @@author CharmaineKoh */
/**
 * Switches view to specified tabPane.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change view to specified tab."
            + "\nParameters: TAB_NAME\n"
            + "Example: " + COMMAND_WORD + " " + "statistics";;

    public static final String MESSAGE_SUCCESS = "Changed view to %s.";

    private final View tabName;

    public ViewCommand(View tabName) {
        this.tabName = tabName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String message = String.format(MESSAGE_SUCCESS, tabName.toString().toLowerCase());

        if (tabName == View.MODULES) {
            return new CommandResult(message,
                    false, false, true, false, false, false, CommandResult.EMPTY_BYTE_ARRAY_DATA);
        } else if (tabName == View.CALENDAR) {
            return new CommandResult(message,
                    false, false, false, true, false, false, CommandResult.EMPTY_BYTE_ARRAY_DATA);
        } else {
            return new CommandResult(message,
                    false, false, false, false, true, false, CommandResult.EMPTY_BYTE_ARRAY_DATA);
        }

    }
}

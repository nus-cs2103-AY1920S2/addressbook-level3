package tatracker.logic.commands.commons;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.model.Model;

/**
 * Switches between tabs
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches to the specified tab.\n"
            + "Example: " + COMMAND_WORD + " Student";

    public static final String SHOWING_GOTO_MESSAGE = "Switched to %s tab.";

    private final String tabName;

    private final Action tabToSwitchTo;

    public GotoCommand(String tabName) {
        this.tabName = tabName.toLowerCase();
        switch(this.tabName) {
        case "student":
            this.tabToSwitchTo = Action.GOTO_STUDENT;
            break;
        case "session":
            this.tabToSwitchTo = Action.GOTO_SESSION;
            break;
        case "claims":
            this.tabToSwitchTo = Action.GOTO_CLAIMS;
            break;
        default:
            assert false;
            tabToSwitchTo = null;
            break;
        }
    }


    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(SHOWING_GOTO_MESSAGE, tabName), tabToSwitchTo);
    }
}

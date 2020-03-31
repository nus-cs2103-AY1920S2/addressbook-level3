package tatracker.logic.commands.commons;

import static tatracker.logic.parser.Prefixes.TAB_NAME;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;

/**
 * Switches between tabs
 */
public class GotoCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.GOTO,
            "Switches to the specified tab.",
            List.of(TAB_NAME),
            List.of(),
            TAB_NAME
    );

    public static final String MESSAGE_SWITCHED_TAB = "Switched to %s tab.";

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
        return new CommandResult(String.format(MESSAGE_SWITCHED_TAB, tabName), tabToSwitchTo);
    }
}

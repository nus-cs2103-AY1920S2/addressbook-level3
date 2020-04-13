//@@author fatin99

package tatracker.logic.commands.commons;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.TAB_NAME;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            "Switches to the specified tab",
            List.of(TAB_NAME),
            List.of(),
            TAB_NAME
    );

    /**
     * Represents the name of a tab view in TA-Tracker.
     */
    public enum Tab {
        STUDENT,
        SESSION,
        CLAIMS;

        public static final String MESSAGE_CONSTRAINTS =
                "These are the only tab names: student, session, claims";

        private static final Map<String, Tab> TABS = Arrays.stream(values())
                .collect(Collectors.toUnmodifiableMap(tab -> tab.name().toLowerCase(), tab -> tab));

        public static boolean isValidTab(String test) {
            return TABS.containsKey(test.toLowerCase());
        }

        public static Tab getTab(String tabName) {
            requireNonNull(tabName);
            return TABS.get(tabName.toLowerCase());
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public static final String MESSAGE_SWITCHED_TAB = "Switched to the %s tab";

    private final Tab tab;
    private final Action tabToSwitchTo;

    public GotoCommand(Tab tab) {
        this.tab = tab;

        switch(tab) {
        case STUDENT:
            tabToSwitchTo = Action.GOTO_STUDENT;
            break;
        case SESSION:
            tabToSwitchTo = Action.GOTO_SESSION;
            break;
        case CLAIMS:
            tabToSwitchTo = Action.GOTO_CLAIMS;
            break;
        default:
            assert false;
            tabToSwitchTo = null;
            break;
        }
    }


    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(MESSAGE_SWITCHED_TAB, tab), tabToSwitchTo);
    }
}

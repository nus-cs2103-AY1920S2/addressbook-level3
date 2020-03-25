package seedu.recipe.logic.commands;

import seedu.recipe.model.Model;
import seedu.recipe.ui.tab.Tab;

/**
 * Switches between the different tabs in HYBB.
 */
public class SwitchCommand extends Command {
    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = SwitchCommand.COMMAND_WORD + ": Switch to a tab\n"
            + "Parameter: recipes/planning/goals/achievements\n"
            + "Example: " + SwitchCommand.COMMAND_WORD + " Planning";

    public static final String MESSAGE_SUCCESS = "Tab successfully switched!";

    private final Tab tab;

    public SwitchCommand(Tab tab) {
        this.tab = tab;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, tab, false);
    }
}


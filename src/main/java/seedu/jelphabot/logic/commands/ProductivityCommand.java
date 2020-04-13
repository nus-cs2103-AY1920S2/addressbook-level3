// @@author Clouddoggo

package seedu.jelphabot.logic.commands;

import seedu.jelphabot.model.Model;

/**
 * Switches to the productivity panel.
 */
public class ProductivityCommand extends Command {

    public static final String COMMAND_WORD = "productivity";
    public static final String COMMAND_SHORTCUT_UPPER = ":P";
    public static final String COMMAND_SHORTCUT_LOWER = ":p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows productivity panel.\n"
                                                   + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT = "Switched to productivity panel.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT).isShowProductivity();
    }
}

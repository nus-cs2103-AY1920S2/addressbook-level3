//@@author yapdianhao
package seedu.jelphabot.logic.commands;

import seedu.jelphabot.model.Model;

/**
 * Switches to the Reminder panel.
 */
public class ReminderTabCommand extends Command {

    public static final String COMMAND_WORD = "remindertab";
    public static final String COMMAND_WORD_UPPER = ":R";
    public static final String COMMAND_WORD_LOWER = ":r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows reminder panel.\n"
                                                   + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT = "Switched to reminder panel.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT).isShowReminder();
    }
}

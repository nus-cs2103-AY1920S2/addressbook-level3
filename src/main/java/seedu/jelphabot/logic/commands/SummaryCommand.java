//@@author eedenong
package seedu.jelphabot.logic.commands;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.summary.Summary;

/**
 * Switches to the summary panel.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String COMMAND_SHORTCUT_UPPER = ":S";
    public static final String COMMAND_SHORTCUT_LOWER = ":s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows summary panel.\n" + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT = "Switched to summary panel";

    @Override
    public CommandResult execute(Model model) {
        model.setSummary(new Summary(model.getFilteredTaskList()));
        return new CommandResult(MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT, false, false).isShowSummary();
    }
}

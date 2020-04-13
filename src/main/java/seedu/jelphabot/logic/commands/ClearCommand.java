package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.summary.Summary;

/**
 * Clears the task list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all tasks in the list permanently.\n"
                                                   + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "JelphaBot has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setJelphaBot(new JelphaBot());
        model.setProductivity(new Productivity(model.getFilteredTaskList(), true, true, true));
        model.setSummary(new Summary(model.getFilteredTaskList()));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

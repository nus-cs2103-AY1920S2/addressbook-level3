package seedu.nova.logic.commands.plannercommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.plan.Task;

/**
 * List task command
 */
public class PlannerStatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";
    private static final String listTitle = "Your tasks inside study plan: \n";

    /**
     * Generate task list string for user
     * @param taskList task list
     * @return string of list of task
     */
    private String listString(List<Task> taskList) {
        StringBuilder ans = new StringBuilder(listTitle);
        for (Task t : taskList) {
            ans.append(t.toString()).append("\n");
        }
        return ans.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(listString(model.getTaskList()));
    }
}

package seedu.address.logic.commands.taskcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Shows the number of tasks for each module in the program.
 */
public class TaskBreakdownCommand extends Command {
    public static final String COMMAND_WORD = "taskBreakdown";

    public static final String MESSAGE_SUCCESS = "Listed number of tasks for each module:\n%s";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        String taskBreakdown = model.getTaskBreakdown();
        System.out.println(model.getTaskBreakdown());
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskBreakdown));
    }
}

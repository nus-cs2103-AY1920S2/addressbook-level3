package seedu.nova.logic.commands.plannercommands;

import static java.util.Objects.requireNonNull;

import java.time.Duration;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.plan.ImpossibleTaskException;
import seedu.nova.model.plan.WeakTask;

/**
 * Add flexible task command
 */
public class PlannerAddFlexibleTaskCommand extends Command {
    public static final String COMMAND_WORD = "flexible";
    private static final String MESSAGE_TASK_ADY_EXIST = "The task already exist in your study plan";
    private static final String MESSAGE_TASK_ADD_SUCCESS = "Task added!";
    private static final String MESSAGE_IMPOSSIBLE_TASK = "You are asking for the impossible";
    private static final String MESSAGE_TASK_ADD_FAILED = "Task NOT added :(";

    private String name;
    private Duration max;
    private Duration min;
    private Duration total;

    public PlannerAddFlexibleTaskCommand(String name, Duration min, Duration max, Duration total) {
        this.name = name;
        this.max = max;
        this.min = min;
        this.total = total;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.searchTask(name) != null) {
            return new CommandResult(MESSAGE_TASK_ADY_EXIST);
        }
        WeakTask task;
        try {
            task = WeakTask.get(name, min, max, total);
        } catch (ImpossibleTaskException ite) {
            return new CommandResult(MESSAGE_IMPOSSIBLE_TASK);
        }
        if (model.addFlexibleTask(task)) {
            return new CommandResult(MESSAGE_TASK_ADD_SUCCESS + "\n" + task);
        } else {
            return new CommandResult(MESSAGE_TASK_ADD_FAILED);
        }
    }
}

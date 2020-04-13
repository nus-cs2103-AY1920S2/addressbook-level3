package seedu.nova.logic.commands.plannercommands;

import static java.util.Objects.requireNonNull;

import java.time.Duration;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.plan.StrongTask;
import seedu.nova.model.plan.TaskFreq;

/**
 * Command for adding routine task to study plan
 */
public class PlannerAddRoutineTaskCommand extends Command {
    public static final String COMMAND_WORD = "routine";
    private static final String MESSAGE_TASK_ADY_EXIST = "The task already exist in your study plan";
    private static final String MESSAGE_TASK_ADD_SUCCESS = "Task added!";
    private static final String MESSAGE_TASK_ADD_FAILED = "Task NOT added :(";

    private String name;
    private Duration duration;
    private TaskFreq freq;

    public PlannerAddRoutineTaskCommand(String name, Duration duration, TaskFreq freq) {
        this.name = name;
        this.duration = duration;
        this.freq = freq;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.searchTask(name) != null) {
            return new CommandResult(MESSAGE_TASK_ADY_EXIST);
        }
        StrongTask task = StrongTask.get(name, duration, freq);
        if (model.addRoutineTask(task)) {
            return new CommandResult(MESSAGE_TASK_ADD_SUCCESS + "\n" + task);
        } else {
            return new CommandResult(MESSAGE_TASK_ADD_FAILED);
        }
    }
}

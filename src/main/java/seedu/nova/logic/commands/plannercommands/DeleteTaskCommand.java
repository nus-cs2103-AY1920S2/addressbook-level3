package seedu.nova.logic.commands.plannercommands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.plan.Task;
import seedu.nova.model.schedule.event.Event;

/**
 * delete a task
 */
public class DeleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private static final String MESSAGE_TASK_DELETE_SUCCESS = "Task deleted!";
    private static final String MESSAGE_TASK_NOT_EXIST = "Task does not exist!";
    private static final String MESSAGE_TASK_DELETE_FAILED = "Task NOT deleted :(";

    private String name;

    public DeleteTaskCommand(String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Task task = model.searchTask(name);
        if (task == null) {
            return new CommandResult(MESSAGE_TASK_NOT_EXIST);
        }
        if (model.deleteTask(task)) {
            for (Event e : task.getEventAfter(LocalDate.now())) {
                try {
                    model.deleteEvent(e);
                } catch (Exception ee) {
                    // do nothing
                }
            }
            return new CommandResult(MESSAGE_TASK_DELETE_SUCCESS + "\n" + task);
        } else {
            return new CommandResult(MESSAGE_TASK_DELETE_FAILED);
        }
    }
}

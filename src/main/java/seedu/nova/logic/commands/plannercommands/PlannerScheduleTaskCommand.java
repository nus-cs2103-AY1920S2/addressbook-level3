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
 * Schedule Task command
 */
public class PlannerScheduleTaskCommand extends Command {
    public static final String COMMAND_WORD = "schedule";
    private static final String MESSAGE_EVENT_GEN_SUCCESS = "New event added: ";
    private static final String MESSAGE_EVENT_GEN_FAILED = "Failed to add new event";
    private static final String MESSAGE_EVENT_ADY_EXIST = "There's already an existing event in the same day/week: ";
    private static final String TASK_NOT_FOUND = "Cannot find task ";

    private String taskName;
    private LocalDate date;

    public PlannerScheduleTaskCommand(String taskName, LocalDate date) {
        this.taskName = taskName;
        this.date = date;
    }

    private String successMsg() {
        return MESSAGE_EVENT_GEN_SUCCESS + date.toString();
    }

    private String taskNotFoundMsg() {
        return TASK_NOT_FOUND + taskName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Task t = model.searchTask(taskName);
        if (t == null) {
            throw new CommandException(taskNotFoundMsg());
        }
        if (t.hasEventOn(date)) {
            return new CommandResult(MESSAGE_EVENT_ADY_EXIST + "\n" + t.getEventOn(date));
        }
        Event event;
        try {
            event = model.generateTaskEvent(t, date);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_EVENT_GEN_FAILED);
        }
        return new CommandResult(MESSAGE_EVENT_GEN_SUCCESS + "\n" + event);
    }
}

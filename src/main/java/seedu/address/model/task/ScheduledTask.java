package seedu.address.model.task;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.task.util.TaskNumManager;
import seedu.address.model.util.Description;

/**
 * Task with a Date and Time (Hours)
 */
public class ScheduledTask extends Task {
    private Description description;
    private ModuleCode moduleCode;
    private TaskDateTime taskDateTime;
    private boolean isDone;
    private int taskNum;

    protected ScheduledTask(Description description, TaskDateTime taskDateTime, ModuleCode moduleCode) {
        this.description = description;
        this.moduleCode = moduleCode.getClone(); // returns a defensive copy
        assert (taskDateTime != null) : "A ScheduledTask must have a valid time frame";
        this.taskDateTime = taskDateTime;
        this.isDone = false;
        this.taskNum = TaskNumManager.getNum(moduleCode);
        TaskNumManager.addNum(moduleCode, taskNum);
    }

    protected ScheduledTask(Description description, TaskDateTime taskDateTime, ModuleCode moduleCode, int taskNum) {
        this.description = description;
        this.moduleCode = moduleCode.getClone(); // returns a defensive copy
        assert (taskDateTime != null) : "A ScheduledTask must have a valid time frame";
        this.taskDateTime = taskDateTime;
        this.isDone = false;
        this.taskNum = taskNum;
        TaskNumManager.addNum(moduleCode, taskNum);
    }

    public ScheduledTask(Description description, TaskDateTime taskDateTime,
                         ModuleCode moduleCode, int taskNum, boolean isDone) {
        this.description = description;
        this.moduleCode = moduleCode.getClone(); // returns a defensive copy
        this.taskDateTime = taskDateTime;
        this.isDone = isDone;
        this.taskNum = taskNum;
        TaskNumManager.addNum(moduleCode, taskNum);
    }

    /**
     * Gets the status icon of our Task.
     */
    private String getStatusIcon() {
        return (isDone ? "Done" : "x"); //return tick or x symbols
    }

    @Override
    public String getTimeString() {
        return taskDateTime.toString();
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public ModuleCode getModuleCode() {
        return moduleCode.getClone(); // returns a defensive copy
    }

    @Override
    public Optional<TaskDateTime> getTaskDateTime() {
        return Optional.of(taskDateTime);
    }

    @Override
    public boolean isTaskDone() {
        return isDone;
    }

    @Override
    public boolean markAsDone() {
        if (isDone) {
            return false;
        }
        isDone = true; // set as done
        return true;
    }

    @Override
    public boolean markAsUndone() {
        if (!isDone) {
            return false;
        }

        isDone = false; // set as undone
        return true;
    }

    @Override
    public int getTaskNum() {
        return this.taskNum;
    }

    @Override
    public Optional<LocalTime> getComparableTime() {
        return Optional.of(taskDateTime.getLocalDateTime().toLocalTime());
    }

    @Override
    public Task getClone() {
        return new ScheduledTask(description, taskDateTime, moduleCode, taskNum, isDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, taskDateTime, moduleCode, isDone);
    }

    @Override
    public String toString() {
        String modShow = String.format("[%s %d]", moduleCode.toString(), taskNum);
        return "[" + getStatusIcon() + "]" + " " + modShow + " " + description.toString()
                + " " + taskDateTime.toString();
    }
}

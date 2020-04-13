package seedu.address.model.task;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.task.util.TaskNumManager;
import seedu.address.model.util.Description;

/**
 * Represents a Task that does not take in any Date or Time.
 */
public class NonScheduledTask extends Task {
    private Description description;
    private ModuleCode moduleCode;
    private boolean isDone;
    private int taskNum;

    protected NonScheduledTask(Description description, ModuleCode moduleCode) {
        this.description = description;
        this.moduleCode = moduleCode.getClone(); // returns a defensive copy
        this.taskNum = TaskNumManager.getNum(moduleCode);
        this.isDone = false;
        TaskNumManager.addNum(moduleCode, taskNum);
    }

    public NonScheduledTask(Description description, ModuleCode moduleCode, int taskNum) {
        this.description = description;
        this.moduleCode = moduleCode.getClone(); // returns a defensive copy
        this.taskNum = taskNum;
        this.isDone = false;
        TaskNumManager.addNum(moduleCode, taskNum);
    }

    public NonScheduledTask(Description description, ModuleCode moduleCode, int taskNum, boolean isDone) {
        this.description = description;
        this.moduleCode = moduleCode.getClone(); // returns a defensive copy
        this.taskNum = taskNum;
        this.isDone = isDone;
        TaskNumManager.addNum(moduleCode, taskNum);
    }

    /**
     * Gets the status icon of our Task.
     */
    private String getStatusIcon() {
        return (isDone ? "Done" : "x");
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
        return Optional.empty();
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
    public Task getClone() {
        return new NonScheduledTask(description, moduleCode, taskNum, isDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, moduleCode, isDone);
    }

    @Override
    public String toString() {
        String modShow = String.format("[%s %d]", moduleCode.toString(), taskNum);
        return "[" + getStatusIcon() + "]" + " " + modShow + " " + description.toString();
    }
}

package seedu.address.model.task;

import java.time.LocalTime;
import java.util.Optional;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.DailySchedulable;
import seedu.address.model.util.Description;

/**
 * Interface for Task class.
 */
public abstract class Task implements DailySchedulable {
    public static final TaskDateTime TABOO_DATE_TIME = new TaskDateTime(TaskDateTime.SPECIAL_DATE_TIME);

    public abstract Description getDescription();
    public abstract ModuleCode getModuleCode();
    public abstract Optional<TaskDateTime> getTaskDateTime();
    public abstract boolean isTaskDone();
    public abstract boolean markAsDone();
    public abstract boolean markAsUndone();
    public abstract int getTaskNum();
    public abstract Task getClone();

    public static ScheduledTask makeScheduledTask(Description description,
                                           TaskDateTime taskDateTime,
                                           ModuleCode moduleCode) {
        return new ScheduledTask(description, taskDateTime, moduleCode);
    }

    public static ScheduledTask makeScheduledTask(Description description,
                                                  TaskDateTime taskDateTime,
                                                  ModuleCode moduleCode,
                                                  int taskNum) {
        return new ScheduledTask(description, taskDateTime, moduleCode, taskNum);
    }

    public static ScheduledTask makeScheduledTask(Description description,
                                                  TaskDateTime taskDateTime,
                                                  ModuleCode moduleCode,
                                                  int taskNum,
                                                  boolean isDone) {
        return new ScheduledTask(description, taskDateTime, moduleCode, taskNum, isDone);
    }

    public static NonScheduledTask makeNonScheduledTask(Description description,
                                                 ModuleCode moduleCode) {
        return new NonScheduledTask(description, moduleCode);
    }

    public static NonScheduledTask makeNonScheduledTask(Description description,
                                                        ModuleCode moduleCode,
                                                        int taskNum) {
        return new NonScheduledTask(description, moduleCode, taskNum);
    }


    public static NonScheduledTask makeNonScheduledTask(Description description,
                                                        ModuleCode moduleCode,
                                                        int taskNum, boolean isDone) {
        return new NonScheduledTask(description, moduleCode, taskNum, isDone);
    }

    public String getTimeString() {
        return "";
    }

    /**
     * Checks if a task is 'similar'.
     * Similarity here implies equal ModuleCode and Description.
     * Tasks with only equal date & time are not considered similar.
     */
    public boolean isSameTask(Task otherTask) {
        // Only checks for similar description and module
        if (otherTask == this) {
            return true;
        }

        if (otherTask == null) {
            return false;
        }

        return otherTask.getModuleCode().equals(getModuleCode())
                && otherTask.getDescription().equals(getDescription());
    }

    @Override
    public boolean equals(Object other) {
        // Checks for everything
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getModuleCode().equals(getModuleCode())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getTaskDateTime().equals(getTaskDateTime());
    }

    @Override
    public Optional<LocalTime> getComparableTime() {
        return Optional.empty();
    }
}

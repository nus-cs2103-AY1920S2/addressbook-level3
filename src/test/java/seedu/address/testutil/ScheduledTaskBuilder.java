package seedu.address.testutil;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.ScheduledTask;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;

/**
 * A utility class to help with building scheduled task objects.
 */
public class ScheduledTaskBuilder {
    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final String DEFAULT_DESCRIPTION = "Programming Assignment";
    public static final String DEFAULT_TASK_DATE_TIME = "01/04/2020";
    public static final boolean DEFAULT_IS_DONE = false;
    private static final int DEFAULT_TASK_ID = 999;

    private ModuleCode moduleCode;
    private Description description;
    private TaskDateTime taskDateTime;
    private boolean isDone;
    private int taskId;

    public ScheduledTaskBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        description = new Description(DEFAULT_DESCRIPTION);
        taskDateTime = new TaskDateTime(DEFAULT_TASK_DATE_TIME);
        isDone = DEFAULT_IS_DONE;
        taskId = DEFAULT_TASK_ID;
    }

    /**
     * Initializes the ScheduledTaskBuilder with the data of {@code taskToCopy}.
     */
    public ScheduledTaskBuilder(ScheduledTask taskToCopy) {
        moduleCode = taskToCopy.getModuleCode();
        description = taskToCopy.getDescription();
        taskDateTime = taskToCopy.getTaskDateTime().get();
        isDone = taskToCopy.isTaskDone();
        taskId = taskToCopy.getTaskNum();
    }

    /**
     * Sets the {@code ModuleCode} of the {@code ScheduledTask} that we are building.
     */
    public ScheduledTaskBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code ScheduledTask} that we are building.
     */
    public ScheduledTaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }


    /**
     * Sets the {@code TaskDateTime} of the {@code ScheduledTask} that we are building.
     */
    public ScheduledTaskBuilder withTaskDateTime(String taskDate) {
        this.taskDateTime = new TaskDateTime(taskDate);
        return this;
    }

    /**
     * Sets the {@code TaskDateTime} of the {@code ScheduledTask} that we are building.
     */
    public ScheduledTaskBuilder withTaskDateTime(String taskDate, String taskTime) {
        this.taskDateTime = new TaskDateTime(taskDate, taskTime);
        return this;
    }

    /**
     * Sets the {@code isDone} of the {@code ScheduledTask} that we are building.
     */
    public ScheduledTaskBuilder setDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    /**
     * Sets the {@code taskId} of the {@code ScheduledTask} that we are building.
     */
    public ScheduledTaskBuilder withTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public ScheduledTask build() {
        return new ScheduledTask(description, taskDateTime, moduleCode, taskId, isDone);
    }
}

package seedu.address.testutil;

import java.util.Optional;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESC = "Do your work!";
    public static final String DEFAULT_MOD_CODE = "CS2101";
    public static final String DEFAULT_DATE = "30/04/1975";
    public static final String DEFAULT_TIME = "17:30";
    public static final int DEFAULT_TASK_NUM = 737;

    private Description description;
    private ModuleCode moduleCode;
    private Optional<TaskDateTime> taskDateTime;
    private int taskNum;

    public TaskBuilder() {
        description = new Description(DEFAULT_DESC);
        moduleCode = new ModuleCode(DEFAULT_MOD_CODE);
        taskDateTime = Optional.of(new TaskDateTime(DEFAULT_DATE, DEFAULT_TIME));
        taskNum = DEFAULT_TASK_NUM;
    }

    /**
     * Initializes the TaskBuilder with data from {@code taskToCopy}
     */
    public TaskBuilder(Task taskToCopy) {
        this.description = taskToCopy.getDescription();
        this.moduleCode = taskToCopy.getModuleCode();
        this.taskDateTime = taskToCopy.getTaskDateTime();
        this.taskNum = taskToCopy.getTaskNum();
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String desc) {
        description = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Task} that we are building.
     */
    public TaskBuilder withModuleCode(String modCode) {
        moduleCode = new ModuleCode(modCode);
        return this;
    }

    /**
     * Sets the {@code TaskDateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDateTime(TaskDateTime tdt) {
        taskDateTime = Optional.ofNullable(tdt);
        return this;
    }

    /**
     * Sets the {@code taskNum} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskNum(int taskNum) {
        this.taskNum = taskNum;
        return this;
    }

    /**
     * Returns the {@code task} that has been built.
     */
    public Task build() {
        if (taskDateTime.isEmpty()) {
            return Task.makeNonScheduledTask(description, moduleCode, taskNum);
        } else {
            return Task.makeScheduledTask(description, taskDateTime.get(), moduleCode, taskNum);
        }
    }
}

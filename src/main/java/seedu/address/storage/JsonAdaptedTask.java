package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;

/**
 * Jackson-friendly version of {@link Task}.
 */
public class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String description;
    private final String taskTime;
    private final String moduleCode;
    private final int taskNum;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description,
                           @JsonProperty("taskTime") String taskTime,
                           @JsonProperty("moduleCode") String moduleCode,
                           @JsonProperty("taskNum") int taskNum,
                           @JsonProperty("isDone") boolean isDone) {
        this.description = description;
        this.taskTime = taskTime;
        this.moduleCode = moduleCode;
        this.taskNum = taskNum;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        this.description = source.getDescription().toString();
        this.taskTime = source.getTimeString();
        this.moduleCode = source.getModuleCode().toString();
        this.taskNum = source.getTaskNum();
        this.isDone = source.isTaskDone();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        if (taskTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        final Description modelDescription = new Description(description);
        if (description != null && !Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);
        if (moduleCode != null && !ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }

        final TaskDateTime modelTaskTime;
        if (taskTime.isEmpty()) {
            return Task.makeNonScheduledTask(modelDescription, modelModuleCode, taskNum, isDone);
        }

        if (!TaskDateTime.isValidTaskTime(taskTime)) {
            throw new IllegalValueException(TaskDateTime.MESSAGE_CONSTRAINTS);
        }

        if (taskTime.length() <= 10) {
            modelTaskTime = new TaskDateTime(taskTime);
        } else {
            modelTaskTime = new TaskDateTime(taskTime.split("\\s")[0], taskTime.split("\\s")[1]);
        }

        return Task.makeScheduledTask(modelDescription, modelTaskTime, modelModuleCode, taskNum, isDone);
    }
}

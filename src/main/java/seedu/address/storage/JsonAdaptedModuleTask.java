package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.Priority;

/**
 * Creates a JsonAdaptedModuleTask for usage
 */
public class JsonAdaptedModuleTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String moduleRelated;
    private final String timing;
    private final String priority;
    private final String description;
    private boolean isDone;

    /**
     * Constructs a {@code ModuleTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedModuleTask(@JsonProperty("moduleRelated") String moduleRelated,
                                 @JsonProperty("timing") String timing,
                                 @JsonProperty("priority") String priority,
                                 @JsonProperty("description") String description,
                                 @JsonProperty("isDone") boolean isDone) {
        this.moduleRelated = moduleRelated;
        this.timing = timing;
        this.priority = priority;
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedModuleTask(ModuleTask source) {
        moduleRelated = source.getModuleRelated().toString();
        timing = source.getDate();
        priority = Integer.toString(source.getPriority().getLevelOfSignificance());
        description = source.getDescription();
        isDone = source.getStatus();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public ModuleTask toModelType() throws IllegalValueException {

        if (moduleRelated == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(moduleRelated)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode moduleCode = new ModuleCode(moduleRelated);

        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "timing"));
        }
        if (!Task.isValidDate(timing)) {
            throw new IllegalValueException("invalid date!");
        }

        if (priority == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = Priority.getPriority(priority);
        ModuleTask result = new ModuleTask(description, moduleCode, timing, modelPriority);

        if (isDone) {
            result.markAsDone();
        }

        return result;
    }
}

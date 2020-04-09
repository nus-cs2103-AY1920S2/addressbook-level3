package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calender.Deadline;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.Priority;


/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedCalendar {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Calendar's %s field is missing!";

    private final String description;
    private final String date;
    private final String category;
    private final String module;
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedCalendar} with the given date details.
     */
    @JsonCreator
    public JsonAdaptedCalendar(@JsonProperty("description") String description, @JsonProperty("date") String date,
                           @JsonProperty("category") String category,
                           @JsonProperty("module") String module,
                           @JsonProperty("priority") String priority) {
        this.description = description;
        this.date = date;
        this.category = category;
        this.module = module;
        this.priority = priority;
    }


    /**
     * Converts a given {@code calendar} into this class for Jackson use.
     */
    public JsonAdaptedCalendar(Task task) {
        description = task.getDescription();
        date = task.getDate();

        if (task instanceof ModuleTask) {
            category = "School";
            priority = ((ModuleTask) task).getPriority().toString();
            module = ((ModuleTask) task).getModuleRelated().toString();
        } else {
            category = task.getCategory();
            priority = "1";
            module = "-1";
        }
    }

    /**
     * Converts this Jackson-friendly adapted Task object into the model's {@code task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Task toModelType() throws IllegalValueException {

        if (this.module.equals("-1")) {
            return new Deadline(description, date, category, "add");
        } else {
            return new ModuleTask(description, new ModuleCode(module), date, Priority.getPriority(priority));

        }
    }

}

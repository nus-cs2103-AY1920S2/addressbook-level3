package seedu.jelphabot.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.task.Task;

/**
 * An Immutable JelphaBot that is serializable to JSON format.
 */
@JsonRootName(value = "jelphabot")
class JsonSerializableJelphaBot {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";

    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableJelphaBot} with the given tasks.
     */
    @JsonCreator
    public JsonSerializableJelphaBot(@JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyJelphaBot} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableJelphaBot}.
     */
    public JsonSerializableJelphaBot(ReadOnlyJelphaBot source) {
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this task list into the model's {@code JelphaBot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public JelphaBot toModelType() throws IllegalValueException {
        JelphaBot jelphaBot = new JelphaBot();
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (jelphaBot.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            jelphaBot.addTask(task);
        }

        return jelphaBot;
    }

}

package seedu.nova.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.model.plan.Task;
import seedu.nova.model.plan.TaskDetails;

/**
 * Writable task
 */
public class JsonAdaptedPlannerTask {
    private static HashMap<TaskDetails, Task> deserializedTask = new HashMap<>();
    private final String serial;

    @JsonCreator
    public JsonAdaptedPlannerTask(@JsonProperty("serial") String serial) {
        this.serial = serial;
    }

    public JsonAdaptedPlannerTask(Task task) {
        String ser = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(task);
            out.close();

            ser = Base64.getEncoder().encodeToString(baos.toByteArray());
            baos.close();
        } catch (IOException ex) {
            // do nothing
        }
        this.serial = ser;
    }

    /**
     * Converts serial back to task
     *
     * @return task
     */
    public Task toTask() throws IllegalValueException {
        Task task;
        try {
            byte[] originSer = Base64.getDecoder().decode(serial);
            InputStream inStream = new ByteArrayInputStream(originSer);
            ObjectInputStream ois = new ObjectInputStream(inStream);
            task = (Task) ois.readObject();
        } catch (Exception e) {
            throw new IllegalValueException("Wrong serial");
        }
        if (deserializedTask.containsKey(task.getDetails())) {
            return deserializedTask.get(task.getDetails());
        } else {
            deserializedTask.put(task.getDetails(), task);
            return task;
        }
    }
}

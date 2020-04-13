package seedu.nova.model.plan;

import java.io.Serializable;
import java.time.Duration;

/**
 * Task details
 */
public class TaskDetails implements Serializable {
    private final String name;
    private final Duration duration;
    private final TaskFreq freq;

    public TaskDetails(String name, Duration duration, TaskFreq freq) {
        this.name = name;
        this.duration = duration;
        this.freq = freq;
    }

    static TaskDetails ofName(String name) {
        return new TaskDetails(name, null, null);
    }

    String getName() {
        return name;
    }

    Duration getDuration() {
        return duration;
    }

    TaskFreq getTaskFreq() {
        return freq;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskDetails) {
            return name.equals(((TaskDetails) obj).name);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        String sb = "Name: "
                + name
                + "Duration: "
                + duration
                + "Frequency: "
                + freq.toString();
        return sb;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}

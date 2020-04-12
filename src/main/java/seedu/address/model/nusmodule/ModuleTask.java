package seedu.address.model.nusmodule;

import java.util.StringJoiner;

import seedu.address.model.calender.Task;

/**
 * Represents a task related to a specific module. (e.g. an assignment of module CS2103T)
 */
public class ModuleTask extends Task {

    private final ModuleCode moduleRelated;
    private final String timing;
    private final Priority priority;

    public ModuleTask(String description, ModuleCode moduleRelated, String timing, Priority priority) {
        super(description);
        this.moduleRelated = moduleRelated;
        this.timing = timing;
        this.priority = priority;
    }

    public ModuleCode getModuleRelated() {
        return this.moduleRelated;
    }

    public String getDate() {
        return timing;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getDoneStatus() {
        return isDone ? 1 : 0;
    }

    public String getDoneMessage() {
        return isDone ? "completed" : "not completed";
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("   ");
        String desiredString = sj.add(this.getDescription()).add(moduleRelated.toString())
                .add(timing.toString()).add(priority.toString()).add(getDoneMessage()).toString();
        return desiredString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleTask)) {
            return false;
        }

        ModuleTask otherTask = (ModuleTask) other;
        return otherTask.getDescription().equals(getDescription())
                && otherTask.getDate().equals(this.getDate())
                && otherTask.getModuleRelated().equals(getModuleRelated())
                && otherTask.getPriority().equals(getPriority());

    }

}

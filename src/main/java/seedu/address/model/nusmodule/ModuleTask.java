package seedu.address.model.nusmodule;

import java.util.StringJoiner;

import seedu.address.calender.Task;

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

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("   ");
        String desiredString = sj.add(this.getDescription()).add(moduleRelated.toString())
                .add(timing.toString()).add(priority.toString()).toString();
        return desiredString;
    }

}

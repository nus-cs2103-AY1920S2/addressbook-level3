package seedu.address.model.nusmodule;

import java.time.LocalDate;

import seedu.address.todolist.Task;

public class ModuleTask extends Task {

    private final ModuleCode moduleRelated;
    private final LocalDate timing;
    private final Priority priority;

    public ModuleTask(String description, ModuleCode moduleRelated, LocalDate timing, Priority priority) {
        super(description);
        this.moduleRelated = moduleRelated;
        this.timing = timing;
        this.priority = priority;
    }

    public ModuleCode getModuleRelated() {
        return this.moduleRelated;
    }

}

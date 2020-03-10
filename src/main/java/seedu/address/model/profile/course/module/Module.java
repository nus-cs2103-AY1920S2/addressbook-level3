package seedu.address.model.profile.course.module;

/**
 * Represents a Module in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    private final ModuleCode moduleCode;
    private final Title title;
    private final PrereqList prereqList;
    private final ModularCredits modularCredits;
    private final Description description;
    private final SemesterData semesterData;

    // Attributes to add
    // Personal { Status, Grade, TaskList }
    private TaskList taskList;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Title title, PrereqList prereqList,
                  ModularCredits modularCredits, Description description, SemesterData semesterData) {
        // requireAllNonNull() // to be implemented
        this.moduleCode = moduleCode;
        this.title = title;
        this.prereqList = prereqList;
        this.modularCredits = modularCredits;
        this.description = description;
        this.semesterData = semesterData;

        this.taskList = taskList;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Title getTitle() {
        return title;
    }

    public PrereqList getPrereqList() {
        return prereqList;
    }

    public ModularCredits getModularCredits() {
        return modularCredits;
    }

    public Description getDescription() {
        return description;
    }

    public SemesterData getSemesterData() {
        return semesterData;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getModuleCode());
        builder.append(": ");
        builder.append(getTitle());

        /*
        builder.append(getPrereqList());
        builder.append(getModularCredits());
        builder.append(getDescription());
        builder.append(getSemesterData());
        builder.append(getAcadYear());
         */

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Module // instanceof handles nulls
                && moduleCode.equals(((Module) other).moduleCode)); // state check
    }
    // methods to be implemented
    // isSameModule()
    // hashCode()
}

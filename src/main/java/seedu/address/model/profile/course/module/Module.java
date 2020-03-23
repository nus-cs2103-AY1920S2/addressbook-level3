package seedu.address.model.profile.course.module;

import java.util.List;

import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.course.module.personal.Personal;

/**
 * Represents a Module in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    private final ModuleCode moduleCode;
    private final Title title;
    private final Prereqs prereqs;
    private final ModularCredits modularCredits;
    private final Description description;
    private final SemesterData semesterData;

    private Personal personal;

    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, Title title, Prereqs prereqs,
                  ModularCredits modularCredits, Description description, SemesterData semesterData) {
        // requireAllNonNull() // to be implemented
        this.moduleCode = moduleCode;
        this.title = title;
        this.prereqs = prereqs;
        this.modularCredits = modularCredits;
        this.description = description;
        this.semesterData = semesterData;

        this.personal = new Personal();
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Personal getPersonal() {
        return personal;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Title getTitle() {
        return title;
    }

    public Prereqs getPrereqs() {
        return prereqs;
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

    public String getStatus() {
        return personal.getStatus();
    }

    public String getGrade() {
        return personal.getGrade();
    }

    public List<Deadline> getDeadlines() {
        return personal.getDeadlines();
    }

    public void deleteDeadline(Deadline deadline) {
        personal.deleteDeadline(deadline);
    }

    // To think of whether Personal stuff should have getters and setters here, since it will be largely repetition

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getModuleCode());
        builder.append(": ");
        builder.append(getTitle());
        builder.append("\n Prerequisite: ");
        builder.append(getPrereqs());
        builder.append("\n Modular Credits (MCs): ");
        builder.append(getModularCredits());
        builder.append("\n Description: ");
        builder.append(getDescription());
        builder.append("\n Semesters Offered: ");
        builder.append(getSemesterData());
        //builder.append(getAcadYear());

        return builder.toString();
    }

    /**
     * Checks for same module code
     * TODO: Check semester too
     */
    public boolean isSameModule(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Module // instanceof handles nulls
                && moduleCode.equals(((Module) other).moduleCode)); // state check
    }
    // && (personal.getSemester() == ((Module) other).getPersonal().getSemester())

    // methods to be implemented
    // isSameModule()
    // hashCode()
}

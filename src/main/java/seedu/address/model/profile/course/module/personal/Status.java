package seedu.address.model.profile.course.module.personal;

/**
 * Keeps track of the current status of the module.
 */
public class Status {

    private ModuleStatus status;

    public Status() {
        this.status = ModuleStatus.NOT_TAKEN;
    }

    public boolean hasNotTaken() {
        return this.status.equals(ModuleStatus.NOT_TAKEN);
    }

    public boolean isTaking() {
        return this.status.equals(ModuleStatus.IN_PROGRESS);
    }

    public boolean hasCompleted() {
        return this.status.equals(ModuleStatus.COMPLETED);
    }

    public void setNotTaken() {
        this.status = ModuleStatus.NOT_TAKEN;
    }

    public void setInProgress() {
        this.status = ModuleStatus.IN_PROGRESS;
    }

    public void setCompleted() {
        this.status = ModuleStatus.COMPLETED;
    }
}

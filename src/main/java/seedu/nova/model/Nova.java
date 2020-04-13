package seedu.nova.model;

import static java.util.Objects.requireNonNull;

import seedu.nova.model.plan.Plan;
import seedu.nova.model.progresstracker.ProgressTracker;

/**
 * Nova class
 */
public class Nova {

    private VersionedAddressBook addressBook;
    private Schedule schedule;
    private ProgressTracker progressTracker;
    private Plan studyPlan;

    public VersionedAddressBook getAddressBookNova() {
        return this.addressBook;
    }

    public void setAddressBookNova(VersionedAddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public Schedule getScheduleNova() {
        return this.schedule;
    }

    public void setScheduleNova(Schedule schedule) {
        this.schedule = schedule;
    }

    public ProgressTracker getProgressTracker() {
        return this.progressTracker;
    }

    public Plan getStudyPlan() {
        return this.studyPlan;
    }

    public void setStudyPlan(Plan plan) {
        this.studyPlan = plan;
    }

    public void setProgressTrackerNova(ProgressTracker progressTracker) {
        requireNonNull(progressTracker);
        this.progressTracker = progressTracker;
    }
}

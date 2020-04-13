package seedu.nova.testutil;

import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.PtTask;

/**
 * A utility class to help with building ProgressTracker objects.
 */
public class PtBuilder {

    private ProgressTracker progressTracker;

    public PtBuilder() {
        progressTracker = new ProgressTracker();
    }

    public PtBuilder(ProgressTracker progressTracker) {
        this.progressTracker = progressTracker;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public PtBuilder withPtTask(PtTask task) {
        progressTracker.addPtTask(task.getProject().getProjectName(), task.getPtWeek(), task);
        return this;
    }

    public ProgressTracker build() {
        return progressTracker;
    }
}

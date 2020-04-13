package seedu.nova.testutil;

import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtNote;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.TaskDesc;

/**
 * A utility class to help with building PtTask objects.
 */
public class PtTaskBuilder {

    public static final Project DEFAULT_PROJECT = new Ip();
    public static final String DEFAULT_TASKDESC = "Example task description";
    public static final String DEFAULT_NOTE = "Example note";
    public static final int DEAFULT_WEEK = 1;


    private Project project;
    private TaskDesc taskDesc;
    private PtNote note;
    private int ptWeek;
    private boolean isDone;

    public PtTaskBuilder() {
        this.project = DEFAULT_PROJECT;
        this.taskDesc = new TaskDesc(DEFAULT_TASKDESC);
        this.note = new PtNote(DEFAULT_NOTE);
        this.ptWeek = DEAFULT_WEEK;
        this.isDone = false;
    }

    public PtTaskBuilder(PtTask source) {
        this.project = source.getProject();
        this.taskDesc = source.getTaskDesc();
        this.note = source.getNote();
        this.ptWeek = source.getPtWeek();
        this.isDone = source.isDone();
    }

    /**
     * Sets the {@code project} of the {@code PtTask} that we are building.
     */
    public PtTaskBuilder withProject(Project project) {
        this.project = project;
        return this;
    }

    /**
     * Sets the {@code taskDesc} of the {@code PtTask} that we are building.
     */
    public PtTaskBuilder withTaskDesc(TaskDesc taskDesc) {
        this.taskDesc = taskDesc;
        return this;
    }

    /**
     * Sets the {@code note} of the {@code PtTask} that we are building.
     */
    public PtTaskBuilder withNote(PtNote note) {
        this.note = note;
        return this;
    }

    /**
     * Sets the {@code ptWeek} of the {@code PtTask} that we are building.
     */
    public PtTaskBuilder withWeek(int weekNum) {
        this.ptWeek = weekNum;
        return this;
    }

    /**
     * Sets the {@code isDone} of the {@code PtTask} that we are building.
     */
    public PtTaskBuilder withIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public PtTask build() {
        return new PtTask(taskDesc, project, note, ptWeek, isDone);
    }
}

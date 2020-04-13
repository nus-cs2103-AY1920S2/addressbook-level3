package seedu.nova.model.progresstracker;

import static java.util.Objects.requireNonNull;

/**
 * Represents a project task
 */
public class PtTask {
    private Project project;
    private TaskDesc taskDesc;
    private PtNote note;
    private int ptWeek;
    private boolean isDone;

    /**
     * Creates PtTask object
     * @param taskDesc taskDesc of task
     * @param project project of task
     * @param note note of task
     * @param ptWeek week of task
     * @param isDone done status of task
     */
    public PtTask(TaskDesc taskDesc, Project project, PtNote note, int ptWeek, boolean isDone) {
        requireNonNull(taskDesc);
        requireNonNull(project);
        requireNonNull(note);

        this.taskDesc = taskDesc;
        this.project = project;
        this.ptWeek = ptWeek;
        this.isDone = isDone;
        this.note = note;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * return string of boolean isDone
     * @return true or false
     */
    public String isDoneString() {
        if (isDone) {
            return "true";
        } else {
            return "false";
        }
    }

    public void setDone() {
        if (isDone) {
            isDone = false;
        } else {
            isDone = true;
        }
    }

    public Project getProject() {
        return project;
    }

    public String getProjectName() {
        return project.getProjectName();
    }

    public void setProject(Project project) {
        requireNonNull(project);
        this.project = project;
    }

    public TaskDesc getTaskDesc() {
        return taskDesc;
    }

    public String getTaskDescString() {
        return taskDesc.toString();
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = new TaskDesc(taskDesc);
    }

    public PtNote getNote() {
        return note;
    }

    public String getNoteString() {
        return note.toString();
    }

    public void setNote(String noteDesc) {
        this.note = new PtNote(noteDesc);
    }

    public int getPtWeek() {
        return ptWeek;
    }

    /**
     * Gets icon for task status.
     * @return icon in string
     */
    public String getStatusIcon() {
        if (isDone) {
            return "\u2713";
        } else {
            return "\u2718";
        }
    }

    /**
     * Returns true if both ptTasks are the same and data fields.
     * This defines a stronger notion of equality between two ptTasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PtTask)) {
            return false;
        }

        PtTask otherPtTask = (PtTask) other;
        return otherPtTask.getProject().getProjectName().equals(getProject().getProjectName())
                && otherPtTask.getNote().toString().equals(getNote().toString())
                && otherPtTask.getTaskDesc().toString().equals(getTaskDesc().toString())
                && (otherPtTask.getPtWeek() == getPtWeek()) && (otherPtTask.isDone() == isDone());
    }


    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getTaskDesc();
    }
}

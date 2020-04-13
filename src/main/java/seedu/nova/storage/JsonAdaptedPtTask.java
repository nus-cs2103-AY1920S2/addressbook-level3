package seedu.nova.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtNote;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.PtWeek;
import seedu.nova.model.progresstracker.TaskDesc;
import seedu.nova.model.progresstracker.Tp;

/**
 * Jackson-friendly version of {@link PtTask}.
 */
public class JsonAdaptedPtTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private String project;
    private String taskDesc;
    private String week;
    private String note;
    private String isDone;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPtTask(@JsonProperty("project") String project, @JsonProperty("taskDesc") String taskDesc,
                             @JsonProperty("week") String week, @JsonProperty("note") String note,
                             @JsonProperty("isDone") String isDone) {
        this.project = project;
        this.taskDesc = taskDesc;
        this.week = week;
        this.note = note;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPtTask(PtTask source) {
        project = source.getProjectName();
        taskDesc = source.getTaskDescString();
        week = String.valueOf(source.getPtWeek());
        note = source.getNoteString();
        isDone = source.isDoneString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code PtTask} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public PtTask toModelType() throws IllegalValueException {
        final Project modelProject;
        final boolean booleanIsDone;

        if (project == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Project.class.getSimpleName()));
        }
        if (!(project.toUpperCase().equals("IP") || project.toUpperCase().equals("TP"))) {
            throw new IllegalValueException(Project.MESSAGE_CONSTRAINTS);
        }
        if (project.toUpperCase().equals("IP")) {
            modelProject = new Ip();
        } else {
            modelProject = new Tp();
        }

        if (taskDesc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDesc.class.getSimpleName()));
        }
        if (!TaskDesc.isValidTaskDesc(taskDesc)) {
            throw new IllegalValueException(TaskDesc.MESSAGE_CONSTRAINTS);
        }
        final TaskDesc modelTaskDesc = new TaskDesc(taskDesc);

        if (week == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PtWeek.class.getSimpleName()));
        }
        if (!PtWeek.isValidWeek(week)) {
            throw new IllegalValueException(PtWeek.MESSAGE_CONSTRAINTS);
        }
        final PtWeek modelPtWeek = new PtWeek(Integer.parseInt(week));

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PtNote.class.getSimpleName()));
        }

        final PtNote modelPtNote = new PtNote(note);

        if (isDone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isDone"));
        }
        if (!(isDone.toLowerCase().equals("false") || isDone.toLowerCase().equals("true"))) {
            throw new IllegalValueException("Not a valid isDone field");
        }
        if (isDone.toLowerCase().equals("true")) {
            booleanIsDone = true;
        } else {
            booleanIsDone = false;
        }

        return new PtTask(modelTaskDesc, modelProject, modelPtNote, modelPtWeek.getWeekNum(), booleanIsDone);
    }
}

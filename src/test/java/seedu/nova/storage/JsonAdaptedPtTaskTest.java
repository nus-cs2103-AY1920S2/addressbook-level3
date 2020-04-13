package seedu.nova.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.nova.storage.JsonAdaptedPtTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalPtTasks.PTTASKIP1;

import org.junit.jupiter.api.Test;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtNote;
import seedu.nova.model.progresstracker.PtWeek;
import seedu.nova.model.progresstracker.TaskDesc;

public class JsonAdaptedPtTaskTest {
    private static final String INVALID_PROJECT = "TTIP";
    private static final String INVALID_TASKDESC = "";
    private static final int INVALID_WEEK = -1;

    private static final String VALID_PROJECT = PTTASKIP1.getProject().getProjectName();
    private static final String VALID_NOTE = PTTASKIP1.getNote().toString();
    private static final String VALID_TASKDESC = PTTASKIP1.getTaskDesc().toString();
    private static final int VALID_WEEK = PTTASKIP1.getPtWeek();

    @Test
    public void toModelType_validPtTaskDetails_returnsPtTask() throws Exception {
        JsonAdaptedPtTask ptTask = new JsonAdaptedPtTask(PTTASKIP1);
        assertEquals(PTTASKIP1, ptTask.toModelType());
    }

    @Test
    public void toModelType_invalidProject_throwsIllegalValueException() {
        JsonAdaptedPtTask ptTask1 = new JsonAdaptedPtTask(null, VALID_TASKDESC,
                String.valueOf(VALID_WEEK), VALID_NOTE, "false");

        String expectedMessage1 = String.format(MISSING_FIELD_MESSAGE_FORMAT, Project.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage1, ptTask1::toModelType);

        JsonAdaptedPtTask ptTask2 = new JsonAdaptedPtTask(INVALID_PROJECT, VALID_TASKDESC,
                String.valueOf(VALID_WEEK), VALID_NOTE, "false");

        String expectedMessage2 = Project.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage2, ptTask2::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedPtTask ptTask1 = new JsonAdaptedPtTask(VALID_PROJECT, VALID_TASKDESC,
                String.valueOf(VALID_WEEK), null, "false");

        String expectedMessage1 = String.format(MISSING_FIELD_MESSAGE_FORMAT, PtNote.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage1, ptTask1::toModelType);
    }

    @Test
    public void toModelType_invalidTaskDesc_throwsIllegalValueException() {
        JsonAdaptedPtTask ptTask1 = new JsonAdaptedPtTask(VALID_PROJECT, null,
                String.valueOf(VALID_WEEK), VALID_NOTE, "false");

        String expectedMessage1 = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDesc.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage1, ptTask1::toModelType);

        JsonAdaptedPtTask ptTask2 = new JsonAdaptedPtTask(VALID_PROJECT, INVALID_TASKDESC,
                String.valueOf(VALID_WEEK), VALID_NOTE, "false");

        String expectedMessage2 = TaskDesc.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage2, ptTask2::toModelType);
    }

    @Test
    public void toModelType_invalidWeek_throwsIllegalValueException() {
        JsonAdaptedPtTask ptTask1 = new JsonAdaptedPtTask(VALID_PROJECT, VALID_TASKDESC,
                null, VALID_NOTE, "false");

        String expectedMessage1 = String.format(MISSING_FIELD_MESSAGE_FORMAT, PtWeek.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage1, ptTask1::toModelType);

        JsonAdaptedPtTask ptTask2 = new JsonAdaptedPtTask(VALID_PROJECT, VALID_TASKDESC,
                String.valueOf(INVALID_WEEK), VALID_NOTE, "false");

        String expectedMessage2 = PtWeek.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage2, ptTask2::toModelType);
    }
}

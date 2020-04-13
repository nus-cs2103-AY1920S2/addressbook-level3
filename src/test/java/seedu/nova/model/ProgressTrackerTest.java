package seedu.nova.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.nova.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.PtNote;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.PtTaskList;
import seedu.nova.model.progresstracker.PtWeek;
import seedu.nova.model.progresstracker.PtWeekList;
import seedu.nova.model.progresstracker.TaskDesc;
import seedu.nova.model.progresstracker.Tp;

public class ProgressTrackerTest {
    private final ProgressTracker progressTracker = new ProgressTracker();

    @Test
    public void constructor() {
        constructorIp();
        constructorTp();
    }

    @Test
    public void constructorIp() {
        Ip ip = progressTracker.getIp();
        PtWeekList ipWeekList = ip.getWeekList();

        assertEquals(new PtWeekList().getNumWeeks(), ipWeekList.getNumWeeks());

        for (PtWeek week : ipWeekList.getWeekList()) {
            PtTaskList ptTaskList = week.getTaskList();

            assertEquals(Collections.emptyList(), ptTaskList.getList());
        }
    }

    @Test
    public void constructorTp() {
        Tp tp = progressTracker.getTp();
        PtWeekList tpWeekList = tp.getWeekList();

        assertEquals(new PtWeekList().getNumWeeks(), tpWeekList.getNumWeeks());

        for (PtWeek week : tpWeekList.getWeekList()) {
            PtTaskList ptTaskList = week.getTaskList();

            assertEquals(Collections.emptyList(), ptTaskList.getList());
        }
    }

    @Test
    public void addPtTask_nullProjectName_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);
        assertThrows(NullPointerException.class, () -> progressTracker.addPtTask(null, 2, task));
    }

    @Test
    public void addPtTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> progressTracker.addPtTask("ip", 2, null));
    }

    @Test
    public void deletePtTask_nullProjectName_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.deletePtTask(null, 2,
                1));
    }

    @Test
    public void editPtTask_nullProjectName_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.editPtTask(null, 2,
                1, "edit"));
    }

    @Test
    public void editPtTask_nullTaskDesc_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProject().getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.editPtTask(null, 2,
                1, null));
    }

    @Test
    public void setDonePtTask_nullProjectName_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.setDonePtTask(null, 2,
                1));
    }

    @Test
    public void addPtNote_nullProjectName_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.addPtNote(null, 2,
                1, "note"));
    }

    @Test
    public void addPtNote_nullNote_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.addPtNote("ip", 2,
                1, null));
    }

    @Test
    public void deletePtNote_nullProjectName_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.deletePtNote(null, 2,
                1));
    }

    @Test
    public void editPtNote_nullProjectName_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.addPtNote(null, 2,
                1, "note"));
    }

    @Test
    public void editPtNote_nullNote_throwsNullPointerException() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);
        assertThrows(NullPointerException.class, () -> progressTracker.addPtNote("ip", 2,
                1, null));
    }

    @Test
    public void addTask_success() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);

        PtTask addedTask = progressTracker.getIp().getWeekList().getWeek(2).getTaskList().getTask(1);
        assertEquals(addedTask, task);
    }

    @Test
    public void deleteTask_success() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);

        int numTask = progressTracker.getIp().getWeekList().getWeek(2).getTaskList().getNumTask();

        assertEquals(numTask, 1);

        progressTracker.deletePtTask(task.getProjectName(), task.getPtWeek(), 1);

        int numTaskAfterDelete = progressTracker.getIp().getWeekList().getWeek(2).getTaskList().getNumTask();
        assertEquals(numTaskAfterDelete, 0);
    }

    @Test
    public void editTask_success() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);

        progressTracker.editPtTask(task.getProjectName(), task.getPtWeek(), 1,
                "newTaskDesc");

        PtTask editedTask = progressTracker.getIp().getWeekList().getWeek(2).getTaskList().getTask(1);
        assertEquals(editedTask.getTaskDesc().toString(), "newTaskDesc");
    }

    @Test
    public void addPtNote_success() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);

        progressTracker.addPtNote(task.getProjectName(), task.getPtWeek(), 1,
                "note");

        PtTask addedNoteTask = progressTracker.getIp().getWeekList().getWeek(2).getTaskList().getTask(1);
        assertEquals(addedNoteTask.getNote().toString(), "note");
    }

    @Test
    public void deletePtNote_success() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);

        progressTracker.deletePtNote(task.getProjectName(), task.getPtWeek(), 1);

        PtTask addedNoteTask = progressTracker.getIp().getWeekList().getWeek(2).getTaskList().getTask(1);
        assertEquals(addedNoteTask.getNote().toString(), "");
    }

    @Test
    public void editPtNote_success() {
        PtTask task = new PtTask(new TaskDesc("taskDesc"), new Ip(), new PtNote(""), 2, false);

        progressTracker.addPtTask(task.getProjectName(), task.getPtWeek(), task);

        progressTracker.addPtNote(task.getProjectName(), task.getPtWeek(), 1, "note");
        progressTracker.editPtNote(task.getProjectName(), task.getPtWeek(), 1, "newNote");

        PtTask addedNoteTask = progressTracker.getIp().getWeekList().getWeek(2).getTaskList().getTask(1);
        assertEquals(addedNoteTask.getNote().toString(), "newNote");
    }


}

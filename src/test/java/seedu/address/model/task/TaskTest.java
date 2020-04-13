package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.task.util.TaskNumManager;
import seedu.address.model.util.Description;

class TaskTest {
    private ModuleCode sampleModCode = new ModuleCode("CS4246");
    private Description sampleDescription = new Description("Project pain");
    private TaskDateTime sampleTaskDateOnly;
    private TaskDateTime sampleTaskDateTime;

    @Test
    void testGetDescription_success() {
        sampleTaskDateOnly = new TaskDateTime("18/03/2020");
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        assertEquals("Project pain",
                Task.makeNonScheduledTask(sampleDescription, sampleModCode).getDescription().toString());
        assertEquals("Project pain",
                Task.makeScheduledTask(sampleDescription, sampleTaskDateOnly, sampleModCode)
                        .getDescription().toString());
        assertEquals("Project pain",
                Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode)
                        .getDescription().toString());
    }

    @Test
    void markAsDone_initiallyNotDoneTask_success() {
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        Task task = Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode);
        assertFalse(task.isTaskDone());
        task.markAsDone();
        assertTrue(task.isTaskDone());
    }

    @Test
    void markAsDone_alreadyDoneTask_notifyTaskMarkedAsDone() {
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        Task task = Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode);
        assertFalse(task.isTaskDone());
        task.markAsDone();
        assertFalse(task.markAsDone()); // false means Task is already done
    }

    @Test
    void isTaskDone_newlyCreatedTask_false() {
        sampleTaskDateOnly = new TaskDateTime("18/03/2020");
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        assertFalse(
                Task.makeNonScheduledTask(sampleDescription, sampleModCode).isTaskDone());
        assertFalse(
                Task.makeScheduledTask(sampleDescription, sampleTaskDateOnly, sampleModCode).isTaskDone());
        assertFalse(
                Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode).isTaskDone());

    }

    @Test
    void isTaskDone_markedAsDoneTask_true() {
        sampleTaskDateOnly = new TaskDateTime("18/03/2020");
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        List<Task> tasks = new ArrayList<>();
        tasks.add(Task.makeNonScheduledTask(sampleDescription, sampleModCode));
        tasks.add(Task.makeScheduledTask(sampleDescription, sampleTaskDateOnly, sampleModCode));
        tasks.add(Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode));
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).markAsDone();
            assertTrue(tasks.get(i).isTaskDone());
        }

        // duplicating markAsDone
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).markAsDone();
            assertTrue(tasks.get(i).isTaskDone());
        }
    }

    @Test
    void isTimeStringEmpty() {
        assertEquals("",
                Task.makeNonScheduledTask(sampleDescription, sampleModCode).getTimeString());
    }

    @Test
    void isTimeStringOnlyDate() {
        sampleTaskDateOnly = new TaskDateTime("18/03/2020");
        assertEquals("18/03/2020",
                Task.makeScheduledTask(sampleDescription, sampleTaskDateOnly, sampleModCode).getTimeString());
    }

    @Test
    void isTimeStringDateTime() {
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        assertEquals("18/03/2020 18:30",
                Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode).getTimeString());
    }

    @Test
    void testToString_taskHasNoTime_success() {
        int num = TaskNumManager.getNum(sampleModCode);
        assertEquals("[x] [CS4246 " + num + "] Project pain",
                Task.makeNonScheduledTask(sampleDescription, sampleModCode, num, false).toString());
        TaskNumManager.removeNum(sampleModCode, num);
    }

    @Test
    void testToString_onlyTaskDateAvailable_success() {
        int num = TaskNumManager.getNum(sampleModCode);
        sampleTaskDateOnly = new TaskDateTime("18/03/2020");
        assertEquals("[x] [CS4246 " + num + "] Project pain 18/03/2020",
                Task.makeScheduledTask(sampleDescription, sampleTaskDateOnly, sampleModCode, num, false).toString());
        TaskNumManager.removeNum(sampleModCode, num);
    }

    @Test
    void testToString_bothTaskDateAndTimeAvailable_success() {
        int num = TaskNumManager.getNum(sampleModCode);
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        assertEquals("[x] [CS4246 " + num + "] Project pain 18/03/2020 18:30",
                Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode, num, false).toString());
        TaskNumManager.removeNum(sampleModCode, num);
    }

    @Test
    void sameTask_test() {
        int num = TaskNumManager.getNum(sampleModCode);
        sampleTaskDateTime = new TaskDateTime("18/03/2020", "18:30");
        Task t1 = Task.makeScheduledTask(sampleDescription, sampleTaskDateTime, sampleModCode, num);
        Task t2 = Task.makeNonScheduledTask(sampleDescription, sampleModCode, num);
        Task t3 = Task.makeNonScheduledTask(new Description("bruh"), sampleModCode, num);
        assertTrue(t1.isSameTask(t2));
        assertFalse(t2.isSameTask(t3));
        assertFalse(t1.isSameTask(t3));
        assertFalse(t3.isSameTask(t2));
        TaskNumManager.removeNum(sampleModCode, num);
    }
}

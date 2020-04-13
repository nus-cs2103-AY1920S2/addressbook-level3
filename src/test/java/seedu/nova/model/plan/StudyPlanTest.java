package seedu.nova.model.plan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.model.Schedule;
import seedu.nova.model.schedule.event.Event;

class StudyPlanTest {

    private static final LocalDate START_DATE = LocalDate.of(2020, 1, 13);
    private static final LocalDate END_DATE = LocalDate.of(2020, 5, 3);
    private static StudyPlan plan = new StudyPlan();
    private static Schedule schedule = new Schedule(START_DATE, END_DATE);

    @Test
    void resetPlan() {
        plan.addTask(StrongTask.get("name", Duration.ZERO, TaskFreq.DAILY));
        plan.resetPlan();
        assertEquals(plan.getTaskList().size(), 0);
    }

    @Test
    void getTaskList() {
        assertNotNull(plan.getTaskList());
    }

    @Test
    void addTask() {
        Task[] taskArr = new Task[]{
                StrongTask.get("fuck", Duration.ZERO, TaskFreq.DAILY),
        };
        for (Task t : taskArr) {
            plan.addTask(t);
            assertTrue(plan.getTaskList().contains(t));
        }
    }

    @Test
    void searchTask() {
        Task[] taskArr = new Task[]{
                StrongTask.get("fuck", Duration.ZERO, TaskFreq.DAILY)
        };
        for (Task t : taskArr) {
            plan.addTask(t);
            assertEquals(plan.searchTask(t.details.getName()), t);
        }
    }

    @Test
    void generateTaskEvent() {
        try {
            Event e = plan.generateTaskEvent(StrongTask.get("fuck", Duration.ZERO, TaskFreq.DAILY),
                    LocalDate.of(2020, 1, 26),
                    schedule);
            assertTrue(
                    schedule.getDay(LocalDate.of(2020, 1, 26)).getEventList().contains(e));
        } catch (Exception e) {
            fail();
        }

        try {
            plan.generateTaskEvent(StrongTask.get("fuck", Duration.ZERO, TaskFreq.DAILY),
                    LocalDate.of(2021, 1, 26),
                    schedule);
            fail();
        } catch (Exception e) {
            //do nothing
        }
    }
}

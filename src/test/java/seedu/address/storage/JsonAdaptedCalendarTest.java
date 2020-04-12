package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTasks.DEADLINE_A_DATE_A;
import static seedu.address.testutil.TypicalTasks.DEADLINE_D_DATE_B;
import static seedu.address.testutil.TypicalTasks.MODULE_TASK;

import org.junit.jupiter.api.Test;

import seedu.address.model.calender.Deadline;
import seedu.address.model.nusmodule.ModuleTask;


public class JsonAdaptedCalendarTest {


    @Test
    public void toModelType_validDetails_returnsCalendar() throws Exception {
        JsonAdaptedCalendar calendar = new JsonAdaptedCalendar(DEADLINE_A_DATE_A);
        assertEquals(DEADLINE_A_DATE_A, calendar.toModelType());
    }

    @Test
    public void toModelType_notDoneTask_returnFalse() throws Exception {
        JsonAdaptedCalendar calendar = new JsonAdaptedCalendar(DEADLINE_D_DATE_B);
        assertFalse(calendar.toModelType().getStatus());
    }

    @Test
    public void toModelType_isModuleTask_returnTrue() throws Exception {
        JsonAdaptedCalendar calendar = new JsonAdaptedCalendar(MODULE_TASK);
        assertTrue(calendar.toModelType() instanceof ModuleTask);
    }

    @Test
    public void toModelType_isNotModuleTask_returnFalse() throws Exception {
        JsonAdaptedCalendar calendar = new JsonAdaptedCalendar(DEADLINE_A_DATE_A);
        assertFalse(calendar.toModelType() instanceof ModuleTask);
    }

    @Test
    public void toModelType_isDeadlineTask_returnTrue() throws Exception {
        JsonAdaptedCalendar calendar = new JsonAdaptedCalendar(DEADLINE_A_DATE_A);
        assertTrue(calendar.toModelType() instanceof Deadline);
    }

    @Test
    public void toModelType_isNotDeadlineTask_returnFalse() throws Exception {
        JsonAdaptedCalendar calendar = new JsonAdaptedCalendar(MODULE_TASK);
        assertFalse(calendar.toModelType() instanceof Deadline);
    }
}

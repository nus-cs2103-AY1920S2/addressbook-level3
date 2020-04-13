package seedu.address.model.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;

public class CalendarTest {

    @Test
    public void getWeek() {
        LocalDate localDate = LocalDate.parse("2020-03-12");
        Calendar calendar = new Calendar(localDate);
        Calendar calendar0 = new Calendar(LocalDate.parse("2020-03-09"));
        Calendar calendar1 = new Calendar(LocalDate.parse("2020-03-10"));
        Calendar calendar2 = new Calendar(LocalDate.parse("2020-03-11"));
        Calendar calendar4 = new Calendar(LocalDate.parse("2020-03-13"));
        Calendar calendar5 = new Calendar(LocalDate.parse("2020-03-14"));
        Calendar calendar6 = new Calendar(LocalDate.parse("2020-03-15"));
        Calendar[] calendars = calendar.getWeek();
        assertEquals(calendars[0], calendar0);
        assertEquals(calendars[1], calendar1);
        assertEquals(calendars[2], calendar2);
        assertEquals(calendars[3], calendar);
        assertEquals(calendars[4], calendar4);
        assertEquals(calendars[5], calendar5);
        assertEquals(calendars[6], calendar6);

    }

    @Test
    public void isWithinDate() {
        LocalDate localDate = LocalDate.parse("2020-03-12");
        Calendar calendar = new Calendar(localDate);
        Task task = Task.makeScheduledTask(new Description("read"),
                new TaskDateTime("12/03/2020"), new ModuleCode("CS2103T"));
        Task otherTask = Task.makeScheduledTask(new Description("read"),
                new TaskDateTime("12/04/2020"), new ModuleCode("CS2103T"));

        //same date -> returns true
        assertTrue(calendar.isWithinDate(task));

        //different date -> returns false
        assertFalse(calendar.isWithinDate(otherTask));
    }

    @Test
    public void equals() {
        LocalDate localDate = LocalDate.parse("2020-02-02");
        Calendar calendar = new Calendar(localDate);
        Calendar otherCalendar = new Calendar(localDate);

        //same date -> returns true
        assertEquals(calendar, otherCalendar);

        //same object -> returns true
        assertEquals(calendar, calendar);

        //null -> returns false
        assertFalse(calendar.equals(null));

        //different type -> return false
        assertFalse(calendar.equals(1));

        //different calendar -> return false
        Calendar diffCalendar = new Calendar(LocalDate.parse("2020-03-03"));
        assertFalse(calendar.equals(diffCalendar));
    }
}

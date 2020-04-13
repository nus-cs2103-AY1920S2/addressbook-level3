package seedu.address.logic.commands.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.calendar.Calendar;

public class CalViewCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CalViewCommand(null));
    }

    @Test
    public void execute_calView_success() {
        CommandResult expectedCommandResult = new CommandResult(
                CalViewCommand.MESSAGE_SUCCESS, CommandType.CALENDAR);
        assertCommandSuccess(new CalViewCommand(
                Calendar.getNowCalendar()), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        CalViewCommand calViewCommand = new CalViewCommand(new Calendar(LocalDate.parse("2020-02-02")));
        CalViewCommand calViewCommandCopy = new CalViewCommand(new Calendar(LocalDate.parse("2020-02-02")));
        CalViewCommand otherCalViewCommand = new CalViewCommand(Calendar.getNextWeekCalendar());

        //same object -> return true
        assertEquals(calViewCommand, calViewCommand);

        //same date -> return true
        assertEquals(calViewCommand, calViewCommandCopy);

        //null -> return false
        assertFalse(calViewCommand.equals(null));

        //different type -> return false
        assertFalse(calViewCommand.equals(1));

        //different date -> return false
        assertFalse(calViewCommand.equals(otherCalViewCommand));

    }
}

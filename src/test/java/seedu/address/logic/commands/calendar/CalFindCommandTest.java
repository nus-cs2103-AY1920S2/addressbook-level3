package seedu.address.logic.commands.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CalFindCommandTest {

    private String getDay(int dayIndex) {
        switch (dayIndex) {
        case 1:
            return "MONDAY";
        case 2:
            return "TUESDAY";
        case 3:
            return "WEDNESDAY";
        case 4:
            return "THURSDAY";
        case 5:
            return "FRIDAY";
        case 6:
            return "SATURDAY";
        default:
            return "SUNDAY";
        }
    }

    @Test
    public void execute_calFind_success() throws CommandException {
        Model tempModel = new ModelManager();
        CommandResult expectedCommandResult = new CalFindCommand().execute(tempModel);
        StringBuilder expectedResult = new StringBuilder(CalFindCommand.MESSAGE_SUCCESS);
        int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        for (int i = currentDayOfWeek; i <= 7; i++) {
            expectedResult.append(getDay(i)).append(":\n00:00-23:59\n");
        }
        assertEquals(expectedCommandResult.getFeedbackToUser(), expectedResult.toString());
    }

}

package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.util.TaskDateTime;


public class TaskDateTimeTest {
    private String validTime1 = "18/03/2020";
    private String validTime2 = "18/03/2020 08:00";
    private String validTime3 = "21/03/2021 19:30";
    private String invalidTime1 = "9/02/2021";
    private String invalidTime2 = "17/2/1970";
    private String invalidTime3 = "1/1/2002";
    private String invalidTime4 = "29/2/29993";
    private String invalidTime5 = "18/03/2020 1:30";

    @Test
    void isValidTaskTimeTest() {
        assertTrue(TaskDateTime.isValidTaskTime(validTime1));
        assertTrue(TaskDateTime.isValidTaskTime(validTime2));
        assertTrue(TaskDateTime.isValidTaskTime(validTime3));

        assertFalse(TaskDateTime.isValidTaskTime(invalidTime1));
        assertFalse(TaskDateTime.isValidTaskTime(invalidTime2));
        assertFalse(TaskDateTime.isValidTaskTime(invalidTime3));
        assertFalse(TaskDateTime.isValidTaskTime(invalidTime4));
        assertFalse(TaskDateTime.isValidTaskTime(invalidTime5));
    }
}

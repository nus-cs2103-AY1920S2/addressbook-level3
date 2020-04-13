package seedu.address.model.calender;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;



class TaskTest {

    private static final String VALID_DATE = "20-04-2020";
    private static final String VALID_DATE_NEXT = "01-03-2020";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task((String) null));
    }

    @Test
    public void isValidDate() {

        // invalid date
        assertFalse(Task.isValidDate("2020-04-20")); //YYYY-MM-DD
        assertFalse(Task.isValidDate("2020-20-04")); //YYYY-DD-MM
        assertFalse(Task.isValidDate("04-20-2020")); //MM-DD-YYYY
        assertFalse(Task.isValidDate("not a date")); // not a date

        // valid date
        assertTrue(Task.isValidDate(VALID_DATE)); // Correct date format DD-MM-YYYY
    }

    @Test
    public void isDone() {
        Task test = new Task("Test");
        test.markAsDone();
        assertTrue(test.getStatus());
    }

    @Test
    public void isTaskPresent() {
        Task.addTaskPerDate(VALID_DATE, new Task("Test task1"));

        assertTrue(Task.isTaskPresent(VALID_DATE));
    }

    @Test
    public void removeTask() {
        Task newTask = new Task("Test Task");
        Task.setDeadlineTaskList(FXCollections.observableList(new ArrayList<Task>()));
        Task.add(newTask);
        Task.remove(newTask);

        // no task should be present
        assertFalse(Task.isTaskPresent(VALID_DATE_NEXT));
    }
}

package nasa.model.activity;

import static nasa.testutil.TypicalActivities.DEADLINE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DeadlineTest {

    @Test
    void create_deadline() throws IllegalArgumentException {
        Deadline deadline = new Deadline(new Name("Hello"), new Date("12-12-2020 23:59"));
        assertTrue(deadline.isValidDeadline(deadline.getDueDate()));
    }

    @Test
    void set_due_date() {
        Date date = new Date("19-12-2020 03:00");
        Deadline subject = (Deadline) DEADLINE;

        assertFalse(subject.getDueDate().equals(date));

        subject.setDueDate(date);
        assertTrue(subject.getDueDate().equals(date));
    }

    @Test
    void set_done() {
        Deadline subject = (Deadline) DEADLINE;
        subject.setDone(true);

        assertTrue(subject.isDone());
    }
}

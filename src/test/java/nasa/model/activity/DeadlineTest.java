package nasa.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static nasa.testutil.TypicalActivities.DEADLINE_LATE;
import org.junit.jupiter.api.Test;

class DeadlineTest {

    @Test
    void create_deadline() {
        Deadline subject = new Deadline(new Name("Test"), new Date("20-04-2020 03:00"), new Note("Practice"));
        subject.updateStatus();
        assertFalse(subject.isLate());
    }

    @Test
    void set_dateline() {
        Date date = new Date("19-03-2020 03:00");
        Deadline subject = (Deadline) DEADLINE_LATE;

        assertFalse(subject.getDateline().equals(date));

        subject.setDateLine(date);
        assertTrue(subject.getDateline().equals(date));
    }
}
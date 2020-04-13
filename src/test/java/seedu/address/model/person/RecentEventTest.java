package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class RecentEventTest {

    @Test
    public void equals() {
        RecentEvent event1 = new RecentEvent("a", "b", "c", "d");
        RecentEvent event2 = new RecentEvent("a", "b", "c", "d");
        RecentEvent event3 = new RecentEvent("e", "b", "c", "d");
        RecentEvent event4 = new RecentEvent("a", "e", "c", "d");
        RecentEvent event5 = new RecentEvent("a", "b", "e", "d");
        RecentEvent event6 = new RecentEvent("a", "b", "c", "e");

        // same object
        assertEquals(event1, event1);

        // same values
        assertEquals(event1, event2);

        // different eventId
        assertNotEquals(event1, event3);

        // different place
        assertNotEquals(event1, event4);

        // different activity
        assertNotEquals(event1, event5);

        // different time
        assertNotEquals(event1, event6);
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class EventDescriptorTest {

    @Test
    public void equals() {
        EventDescriptor eventDescriptor1 = new EventDescriptor("abc");
        EventDescriptor eventDescriptor2 = new EventDescriptor("abc");
        EventDescriptor eventDescriptor3 = new EventDescriptor("def");
        EventDescriptor eventDescriptor4 = new EventDescriptor("abc", 2);

        // same object
        assertEquals(eventDescriptor1, eventDescriptor1);

        // same values
        assertEquals(eventDescriptor1, eventDescriptor2);

        // different names
        assertNotEquals(eventDescriptor1, eventDescriptor3);

        // different frequencies
        assertNotEquals(eventDescriptor1, eventDescriptor4);
    }

    @Test
    public void increment() {
        EventDescriptor eventDescriptor1 = new EventDescriptor("abc");
        EventDescriptor eventDescriptor2 = new EventDescriptor("abc", 2);
        eventDescriptor1.increment();
        assertEquals(eventDescriptor1, eventDescriptor2);
    }
}

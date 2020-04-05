package seedu.address.testutil.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventHistory;
import seedu.address.model.event.RecurrenceType;


/**
 * A utility class containing a list of {@code Events} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT1 = new EventBuilder()
            .withEventName("Sleep abit cuz tired")
            .withStartDateTime(LocalDateTime.parse("2020-04-01T08:00"))
            .withEndDateTime(LocalDateTime.parse("2020-04-01T10:00"))
            .withColorCode("group01").withUniqueIdentifier("eventTest1")
            .withRecurrenceType(RecurrenceType.NONE).build();

    public static final Event EVENT2 = new EventBuilder()
            .withEventName("Shower cuz smelly")
            .withStartDateTime(LocalDateTime.parse("2020-04-02T08:00"))
            .withEndDateTime(LocalDateTime.parse("2020-04-02T10:00"))
            .withColorCode("group02")
            .withUniqueIdentifier("eventTest2")
            .withRecurrenceType(RecurrenceType.NONE).build();

    public static final Event EVENT3 = new EventBuilder()
            .withEventName("Run cuz feeling fat")
            .withStartDateTime(LocalDateTime.parse("2020-04-03T08:00"))
            .withEndDateTime(LocalDateTime.parse("2020-04-03T10:00"))
            .withColorCode("group03")
            .withUniqueIdentifier("eventTest3")
            .withRecurrenceType(RecurrenceType.NONE).build();


    public static final Event NON_TYPICAL_EVENT = new EventBuilder()
            .withEventName("Not Typical Event")
            .withStartDateTime(LocalDateTime.parse("2020-04-04T08:00"))
            .withEndDateTime(LocalDateTime.parse("2020-04-04T10:00"))
            .withColorCode("group04")
            .withUniqueIdentifier("eventTestNonTypical")
            .withRecurrenceType(RecurrenceType.NONE).build();


    private TypicalEvents() {
    }

    /**
     * Returns an {@code EventRecord} with all the typical events.
     */
    public static EventHistory getTypicalEventHistory() {
        EventHistory ab = new EventHistory(getTypicalEvents());
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2, EVENT3));
    }

}

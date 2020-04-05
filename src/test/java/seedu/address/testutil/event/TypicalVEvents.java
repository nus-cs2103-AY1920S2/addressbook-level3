package seedu.address.testutil.event;

import static seedu.address.commons.util.EventUtil.DAILY_RECUR_RULE;
import static seedu.address.commons.util.EventUtil.NO_RECUR_RULE;
import static seedu.address.commons.util.EventUtil.WEEKLY_RECUR_RULE;

import java.time.LocalDateTime;

import jfxtras.icalendarfx.components.VEvent;

/**
 * A utility class containing a list of {@code VEvents} objects to be used in tests.
 */
public class TypicalVEvents {
    public static final VEvent VEVENT1 = new VEvent()
            .withSummary("Sleep abit cuz tired")
            .withDateTimeStart(LocalDateTime.parse("2020-04-01T08:00"))
            .withDateTimeEnd(LocalDateTime.parse("2020-04-01T10:00"))
            .withCategories("group01")
            .withUniqueIdentifier("eventTest1")
            .withRecurrenceRule(NO_RECUR_RULE);

    public static final VEvent VEVENT2 = new VEvent()
            .withSummary("Shower cuz smelly")
            .withDateTimeStart(LocalDateTime.parse("2020-04-02T08:00"))
            .withDateTimeEnd(LocalDateTime.parse("2020-04-01T10:00"))
            .withCategories("group02")
            .withUniqueIdentifier("eventTest2")
            .withRecurrenceRule(DAILY_RECUR_RULE);

    public static final VEvent NON_TYPICAL_VEVENT = new VEvent()
            .withSummary("Not Typical Event")
            .withDateTimeStart(LocalDateTime.parse("2020-04-03T08:00"))
            .withDateTimeEnd(LocalDateTime.parse("2020-04-03T10:00"))
            .withCategories("group04")
            .withUniqueIdentifier("eventTestNonTypical")
            .withRecurrenceRule(WEEKLY_RECUR_RULE);


}

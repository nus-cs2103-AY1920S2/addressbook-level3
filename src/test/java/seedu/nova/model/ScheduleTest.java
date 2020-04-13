package seedu.nova.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalEvents.MEETING;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Meeting;
import seedu.nova.model.schedule.event.exceptions.DateNotFoundException;
import seedu.nova.model.schedule.event.exceptions.EventNotFoundException;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;

class ScheduleTest {

    private static final LocalDate START_DATE = LocalDate.of(2020, 1, 13);
    private static final LocalDate END_DATE = LocalDate.of(2020, 5, 3);

    private static Schedule schedule = new Schedule(START_DATE, END_DATE);

    @AfterEach
    void cleanUpEach() {
        schedule = new Schedule(START_DATE, END_DATE);
    }

    @Test
    void view_dateWithoutEvents() {

        LocalDate testDate = LocalDate.of(2020, 3, 5);
        String result = schedule.view(testDate);
        assertEquals("", result);
    }

    @Test
    void view_dateWithEvents() {

        LocalDate testDate = LocalDate.of(2020, 3, 5);
        Event sampleEvent = new Meeting("Test Description", "Test Venue", LocalTime.of(14, 0),
                LocalTime.of(16, 0), testDate);

        schedule.addEvent(sampleEvent);

        String result = schedule.view(testDate);
        assertEquals("1. " + sampleEvent.toString() + "\n", result);
    }

    @Test
    void view_normalWeek_noEvents() {

        int testWeek = 7;
        String result = schedule.view(testWeek);
        assertEquals("", result);
    }

    @Test
    void view_recessWeek_noEvents() {
        int testWeek = 16;
        String result = schedule.view(testWeek);
        assertEquals("", result);
    }

    @Test
    void checkDateValidity_validDate_true() {

        LocalDate testDate = LocalDate.of(2020, 3, 5);
        boolean result = schedule.checkDateValidity(testDate);
        assertTrue(result);
    }

    @Test
    void checkDateValidity_borderStartDate_true() {

        boolean result = schedule.checkDateValidity(START_DATE);
        assertTrue(result);

    }
    @Test
    void checkDateValidity_beforeBorderStartDate_false() {

        LocalDate testDate = START_DATE.minusDays(1);
        boolean result = schedule.checkDateValidity(testDate);
        assertFalse(result);

    }

    @Test
    void checkDateValidity_borderEndDate_true() {

        boolean result = schedule.checkDateValidity(END_DATE);
        assertTrue(result);
    }

    @Test
    void checkDateValidity_afterBorderEndDate_false() {

        LocalDate testDate = END_DATE.plusDays(1);
        boolean result = schedule.checkDateValidity(testDate);
        assertFalse(result);
    }

    @Test
    void checkWeekValidity_validWeek_true() {

        int testWeek = 7;
        boolean result = schedule.checkWeekValidity(testWeek);
        assertTrue(result);
    }

    @Test
    void checkWeekValidity_startWeek_true() {

        int testWeek = 1;
        boolean result = schedule.checkWeekValidity(testWeek);
        assertTrue(result);
    }

    @Test
    void checkWeekValidity_beforeStartWeek_false() {

        int testWeek = 0;
        boolean result = schedule.checkWeekValidity(testWeek);
        assertFalse(result);
    }

    @Test
    void checkWeekValidity_endWeek_true() {

        int testWeek = 16;
        boolean result = schedule.checkWeekValidity(testWeek);
        assertTrue(result);
    }

    @Test
    void checkWeekValidity_afterEndWeek_false() {

        int testWeek = 17;
        boolean result = schedule.checkWeekValidity(testWeek);
        assertFalse(result);
    }

    @Test
    public void addEvent_success() {
        schedule.addEvent(MEETING);

        assertEquals(schedule.getEventList(), Arrays.asList(MEETING));

    }

    @Test
    public void addEvent_existingEventAtTime_throwsTimeOverlapException() {
        schedule.addEvent(MEETING);

        assertThrows(TimeOverlapException.class, () -> schedule.addEvent(MEETING));

    }

    @Test
    public void deleteEvent_success() {
        schedule.addEvent(MEETING);

        schedule.deleteEvent(LocalDate.parse("2020-03-09"), 0);
        assertEquals(schedule.getEventList(), Arrays.asList());

    }

    @Test
    public void deleteEvent_dateWithNoEvent_throwsException() {
        assertThrows(DateNotFoundException.class, () -> schedule.deleteEvent(LocalDate.parse("2020-03-09"), 0));

    }

    @Test
    public void deleteEvent_invalidIndex_throwsException() {
        schedule.addEvent(MEETING);

        assertThrows(EventNotFoundException.class, () -> schedule.deleteEvent(LocalDate.parse("2020-03-09"), 1));

    }

    @Test
    public void addNote_success() {
        schedule.addEvent(MEETING);

        schedule.addNote("this is a note", LocalDate.parse("2020-03-09"), 0);

        MEETING.addNote("this is a note");
        assertEquals(schedule.getEventList(), Arrays.asList(MEETING));

    }

    @Test
    public void addNote_dateWithNoEvent_throwsException() {
        assertThrows(DateNotFoundException.class, () ->
                schedule.addNote("this is a note", LocalDate.parse("2020-03-09"), 0));

    }

    @Test
    public void addNote_invalidIndex_throwsException() {
        schedule.addEvent(MEETING);

        assertThrows(EventNotFoundException.class, () ->
                schedule.addNote("this is a note", LocalDate.parse("2020-03-09"), 1));
    }

}

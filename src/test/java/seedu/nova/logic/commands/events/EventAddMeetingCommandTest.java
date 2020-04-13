package seedu.nova.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalEvents.MEETING;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddMeetingCommand;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Meeting;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;
import seedu.nova.testutil.ModelStub;


public class EventAddMeetingCommandTest {
    private static final Event invalidMeeting = new Meeting("Project Meeting", "COM1-B108",
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalDate.of(2020, 5, 20));


    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddMeetingCommand(null));
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ModelStubAcceptingMeetingAdded();

        Event validMeeting = MEETING;

        CommandResult commandResult = new EventAddMeetingCommand(validMeeting).execute(modelStub);

        assertEquals(String.format(EventAddMeetingCommand.MESSAGE_SUCCESS, validMeeting),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validMeeting), modelStub.eventsAdded);
    }

    @Test
    public void execute_consultationOverlapsWithOtherEvent_throwsCommandException() {

        ModelStubOverlappingEvent modelStub = new ModelStubOverlappingEvent();

        EventAddMeetingCommand eventAddMeetingCommand = new EventAddMeetingCommand(MEETING);

        assertThrows(CommandException.class, EventAddMeetingCommand.MESSAGE_TIME_OVERLAP, () ->
                eventAddMeetingCommand.execute(modelStub));

    }

    @Test
    public void execute_consultationHasDateOutsideOfSemester_throwsCommandException() {
        ModelStubOutOfSemesterDate modelStub = new ModelStubOutOfSemesterDate();

        EventAddMeetingCommand eventAddMeetingCommand = new EventAddMeetingCommand(invalidMeeting);

        assertThrows(CommandException.class, EventAddMeetingCommand.MESSAGE_INVALID_DATE, () ->
                eventAddMeetingCommand.execute(modelStub));

    }

    @Test
    public void equals() {
        Event meeting1 = MEETING;

        Event meeting2 = new Meeting("Project Meeting", "COM1-B108",
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalDate.of(2020, 4, 9));


        EventAddMeetingCommand command1 = new EventAddMeetingCommand(meeting1);
        EventAddMeetingCommand command2 = new EventAddMeetingCommand(meeting2);

        assertTrue(command1.equals(command1));

        EventAddMeetingCommand command1Copy = new EventAddMeetingCommand(meeting1);

        assertTrue(command1.equals(command1Copy));

        assertFalse(command1.equals("blah"));

        assertFalse(command1.equals(null));

        assertFalse(command1.equals(command2));
    }

    private class ModelStubOverlappingEvent extends ModelStub {
        @Override
        public boolean isWithinSem(LocalDate date) {
            return true;
        }

        @Override
        public void addEvent(Event e) {
            requireNonNull(e);

            throw new TimeOverlapException();
        }
    }

    private class ModelStubOutOfSemesterDate extends ModelStub {
        @Override
        public boolean isWithinSem(LocalDate date) {
            return false;
        }
    }

    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        private List<Event> eventsAdded = new LinkedList<>();

        @Override
        public boolean isWithinSem(LocalDate date) {
            return true;
        }

        @Override
        public void addEvent(Event e) {
            requireNonNull(e);

            eventsAdded.add(e);
        }
    }

}

package seedu.nova.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalEvents.MEETING;
import static seedu.nova.testutil.TypicalEvents.STUDY_SESSION;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddStudyCommand;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Meeting;
import seedu.nova.model.schedule.event.StudySession;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;
import seedu.nova.testutil.ModelStub;

public class EventAddStudyCommandTest {
    private static final Event invalidStudy = new StudySession("UML Diagrams", "Home",
            LocalTime.of(11, 0), LocalTime.of(12, 0), LocalDate.of(2020, 5, 20));

    @Test
    public void constructor_nullStudy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddStudyCommand(null));
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudyAdded modelStub = new ModelStubAcceptingStudyAdded();

        Event validStudy = STUDY_SESSION;

        CommandResult commandResult = new EventAddStudyCommand(validStudy).execute(modelStub);

        assertEquals(String.format(EventAddStudyCommand.MESSAGE_SUCCESS, validStudy),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validStudy), modelStub.eventsAdded);
    }

    @Test
    public void execute_consultationOverlapsWithOtherEvent_throwsCommandException() {

        ModelStubOverlappingEvent modelStub = new ModelStubOverlappingEvent();

        EventAddStudyCommand eventAddStudyCommand = new EventAddStudyCommand(MEETING);

        assertThrows(CommandException.class, EventAddStudyCommand.MESSAGE_TIME_OVERLAP, () ->
                eventAddStudyCommand.execute(modelStub));

    }

    @Test
    public void execute_consultationHasDateOutsideOfSemester_throwsCommandException() {
        ModelStubOutOfSemesterDate modelStub = new ModelStubOutOfSemesterDate();

        EventAddStudyCommand eventAddStudyCommand = new EventAddStudyCommand(invalidStudy);

        assertThrows(CommandException.class, EventAddStudyCommand.MESSAGE_INVALID_DATE, () ->
                eventAddStudyCommand.execute(modelStub));

    }

    @Test
    public void equals() {
        Event study1 = STUDY_SESSION;

        Event study2 = new Meeting("Project Meeting", "COM1-B108",
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalDate.of(2020, 4, 9));


        EventAddStudyCommand command1 = new EventAddStudyCommand(study1);
        EventAddStudyCommand command2 = new EventAddStudyCommand(study2);

        assertTrue(command1.equals(command1));

        EventAddStudyCommand command1Copy = new EventAddStudyCommand(study1);

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

    private class ModelStubAcceptingStudyAdded extends ModelStub {
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

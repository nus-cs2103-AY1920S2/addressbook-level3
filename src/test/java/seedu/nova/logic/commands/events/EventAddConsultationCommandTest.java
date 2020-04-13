package seedu.nova.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalEvents.CONSULTATION;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddConsultationCommand;
import seedu.nova.model.schedule.event.Consultation;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;
import seedu.nova.testutil.ModelStub;


public class EventAddConsultationCommandTest {
    private static final Event invalidConsultation = new Consultation("Design Principles", "COM2-0203",
            LocalTime.of(16, 0), LocalTime.of(17, 0),
            LocalDate.of(2020, 5, 20));

    @Test
    public void constructor_nullConsultation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddConsultationCommand(null));
    }

    @Test
    public void execute_consultationAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingConsultationAdded modelStub = new ModelStubAcceptingConsultationAdded();

        Event validConsultation = CONSULTATION;

        CommandResult commandResult = new EventAddConsultationCommand(validConsultation).execute(modelStub);

        assertEquals(String.format(EventAddConsultationCommand.MESSAGE_SUCCESS, validConsultation),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validConsultation), modelStub.eventsAdded);
    }

    @Test
    public void execute_consultationOverlapsWithOtherEvent_throwsCommandException() {

        ModelStubOverlappingEvent modelStub = new ModelStubOverlappingEvent();

        EventAddConsultationCommand eventAddConsultationCommand = new EventAddConsultationCommand(CONSULTATION);

        assertThrows(CommandException.class, EventAddConsultationCommand.MESSAGE_TIME_OVERLAP, () ->
                eventAddConsultationCommand.execute(modelStub));

    }

    @Test
    public void execute_consultationHasDateOutsideOfSemester_throwsCommandException() {
        ModelStubOutOfSemesterDate modelStub = new ModelStubOutOfSemesterDate();

        EventAddConsultationCommand eventAddConsultationCommand = new EventAddConsultationCommand(invalidConsultation);

        assertThrows(CommandException.class, EventAddConsultationCommand.MESSAGE_INVALID_DATE, () ->
                eventAddConsultationCommand.execute(modelStub));

    }

    @Test
    public void equals() {
        Event consultation1 = CONSULTATION;

        Event consultation2 = new Consultation("Design Principles", "COM2-0203",
                LocalTime.of(16, 0), LocalTime.of(17, 0),
                LocalDate.of(2020, 2, 20));

        EventAddConsultationCommand command1 = new EventAddConsultationCommand(consultation1);
        EventAddConsultationCommand command2 = new EventAddConsultationCommand(consultation2);

        assertTrue(command1.equals(command1));

        EventAddConsultationCommand command1Copy = new EventAddConsultationCommand(consultation1);

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

    private class ModelStubAcceptingConsultationAdded extends ModelStub {
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

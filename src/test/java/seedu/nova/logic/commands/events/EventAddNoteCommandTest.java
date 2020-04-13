package seedu.nova.logic.commands.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.nova.testutil.Assert.assertThrows;
import static seedu.nova.testutil.TypicalEvents.MEETING;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddNoteCommand;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.exceptions.DateNotFoundException;
import seedu.nova.model.schedule.event.exceptions.EventNotFoundException;
import seedu.nova.testutil.ModelStub;

public class EventAddNoteCommandTest {

    private static final String VALID_DESC = "Bring charger";
    private static final LocalDate VALID_DATE = LocalDate.parse("2020-03-09");
    private static final LocalDate INVALID_DATE = LocalDate.parse("2020-06-09");

    @Test
    public void execute_validIndexValidDate_success() throws Exception {
        // adding a note to an event of index 1 from a list with one event
        ModelStubAcceptingNote modelStub = new ModelStubAcceptingNote();

        CommandResult commandResult = new EventAddNoteCommand(VALID_DESC, VALID_DATE, INDEX_FIRST_EVENT)
                .execute(modelStub);

        MEETING.addNote(VALID_DESC);

        assertEquals(String.format(EventAddNoteCommand.MESSAGE_SUCCESS, MEETING.toString()),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(MEETING), modelStub.events);

    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // trying to add a note to index 1 in an empty list
        ModelStubInvalidIndex modelStub = new ModelStubInvalidIndex();

        EventAddNoteCommand eventAddNoteCommand = new EventAddNoteCommand(VALID_DESC, VALID_DATE, INDEX_FIRST_EVENT);

        assertThrows(CommandException.class, EventAddNoteCommand.MESSAGE_INVALID_INDEX_DATE, () ->
                eventAddNoteCommand.execute(modelStub));

    }

    @Test
    public void execute_outOfSemDate_throwsCommandException() {
        // trying to add a note with a date that is outside of the bounds of the semester

        ModelStubOutOfSem modelStub = new ModelStubOutOfSem();

        EventAddNoteCommand eventAddNoteCommand = new EventAddNoteCommand(VALID_DESC, INVALID_DATE, INDEX_FIRST_EVENT);

        assertThrows(CommandException.class, EventAddNoteCommand.MESSAGE_INVALID_DATE, () ->
                eventAddNoteCommand.execute(modelStub));
    }

    @Test
    public void execute_dateWithNoEvent_throwsCommandException() {
        // trying to add a note with a date that has no events

        ModelStubDateWithNoEvents modelStub = new ModelStubDateWithNoEvents();

        EventAddNoteCommand eventAddNoteCommand = new EventAddNoteCommand(VALID_DESC, VALID_DATE, INDEX_FIRST_EVENT);

        assertThrows(CommandException.class, EventAddNoteCommand.MESSAGE_NO_EVENT, () ->
                eventAddNoteCommand.execute(modelStub));
    }

    private class ModelStubAcceptingNote extends ModelStub {
        private List<Event> events = new LinkedList<>(Arrays.asList(MEETING));

        @Override
        public boolean isWithinSem(LocalDate date) {
            return true;
        }

        @Override
        public String addNote(String desc, LocalDate date, int index) {
            events.get(index).addNote(desc);

            return events.get(index).toString();
        }

    }

    private class ModelStubInvalidIndex extends ModelStub {
        private List<Event> events = new LinkedList<>();

        @Override
        public boolean isWithinSem(LocalDate date) {
            return true;
        }

        @Override
        public String addNote(String desc, LocalDate date, int index) {
            throw new EventNotFoundException();
        }


    }

    private class ModelStubDateWithNoEvents extends ModelStub {
        @Override
        public boolean isWithinSem(LocalDate date) {
            return true;
        }

        @Override
        public String addNote(String desc, LocalDate date, int index) {
            throw new DateNotFoundException();
        }


    }

    private class ModelStubOutOfSem extends ModelStub {

        @Override
        public boolean isWithinSem(LocalDate date) {
            return false;
        }

    }

}

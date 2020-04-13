package seedu.nova.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddConsultationCommand;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddLessonCommand;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;
import seedu.nova.testutil.ModelStub;

public class EventAddLessonCommandTest {
    private static final Event validLesson = new Lesson("CS2103T Lecture", "I3 AUD",
            LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.parse("2020-04-10"), DayOfWeek.FRIDAY);

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventAddLessonCommand(null));
    }


    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();

        CommandResult commandResult = new EventAddLessonCommand(validLesson).execute(modelStub);

        assertEquals(String.format(EventAddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validLesson), modelStub.eventsAdded);
    }

    @Test
    public void execute_lessonOverlapsWithOtherEvent_throwsCommandException() {

        ModelStubOverlappingEvent modelStub = new ModelStubOverlappingEvent();

        EventAddLessonCommand eventAddLessonCommand = new EventAddLessonCommand(validLesson);

        assertThrows(CommandException.class, EventAddConsultationCommand.MESSAGE_TIME_OVERLAP, () ->
                eventAddLessonCommand.execute(modelStub));

    }


    @Test
    public void equals() {

        Event lesson2 = new Lesson("CS2103T Lecture", "I3 AUD",
                LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.parse("2020-04-06"), DayOfWeek.MONDAY);

        EventAddLessonCommand command1 = new EventAddLessonCommand(validLesson);
        EventAddLessonCommand command2 = new EventAddLessonCommand(lesson2);

        assertTrue(command1.equals(command1));

        EventAddLessonCommand command1Copy = new EventAddLessonCommand(validLesson);

        assertTrue(command1.equals(command1Copy));

        assertFalse(command1.equals("blah"));

        assertFalse(command1.equals(null));

        assertFalse(command1.equals(command2));
    }

    private class ModelStubOverlappingEvent extends ModelStub {

        @Override
        public void addAllLessons(Lesson lesson) {
            requireNonNull(lesson);

            throw new TimeOverlapException();
        }
    }


    private class ModelStubAcceptingLessonAdded extends ModelStub {
        private List<Event> eventsAdded = new LinkedList<>();

        @Override
        public void addAllLessons(Lesson lesson) {
            requireNonNull(lesson);

            eventsAdded.add(lesson);
        }
    }


}

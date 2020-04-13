package seedu.nova.logic.commands.sccommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.nova.testutil.TypicalEvents.MEETING;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Schedule;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.testutil.ModelStub;

class ScViewWeekCommandTest {

    private static final LocalDate SCHEDULE_START_DATE = LocalDate.of(2020, 1, 13);
    private static final LocalDate SCHEDULE_END_DATE = LocalDate.of(2020, 5, 3);

    @Test
    void execute_validWeekNoEvent_viewSuccessful() throws Exception {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        CommandResult commandResult = new ScViewWeekCommand(6).execute(modelStub);
        assertEquals(ScViewWeekCommand.MESSAGE_WEEK_NO_EVENT, commandResult.getFeedbackToUser());
    }

    @Test
    void execute_validWeekWithEvent_viewSuccessful() throws Exception {

        final String expectedMessage = MEETING.getDayOfWeek() + ": \n" + "1. " + MEETING + "\n\n";

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        modelStub.addEvent(MEETING);
        CommandResult commandResult = new ScViewWeekCommand(8).execute(modelStub);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    void execute_weekBeforeStart_throwsCommandException() {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        assertThrows(CommandException.class, () -> new ScViewWeekCommand(0).execute(modelStub));
    }

    @Test
    void execute_weekAfterEnd_throwsCommandException() {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        assertThrows(CommandException.class, () -> new ScViewWeekCommand(17).execute(modelStub));
    }

    private class ModelStubWithSchedule extends ModelStub {

        private Schedule schedule = new Schedule(SCHEDULE_START_DATE, SCHEDULE_END_DATE);

        @Override
        public void addEvent(Event e) {

            schedule.addEvent(e);
        }

        @Override
        public boolean isWithinSem(int weekNumber) {

            return schedule.checkWeekValidity(weekNumber);

        }

        @Override
        public String viewSchedule(int weekNumber) {

            return schedule.view(weekNumber);
        }
    }

}

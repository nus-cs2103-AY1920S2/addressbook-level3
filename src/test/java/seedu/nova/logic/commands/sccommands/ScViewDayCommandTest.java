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

class ScViewDayCommandTest {

    private static final LocalDate SCHEDULE_START_DATE = LocalDate.of(2020, 1, 13);
    private static final LocalDate SCHEDULE_END_DATE = LocalDate.of(2020, 5, 3);

    @Test
    void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScViewDayCommand(null));
    }

    @Test
    void execute_validDateNoEvent_viewSuccessful() throws Exception {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        CommandResult commandResult = new ScViewDayCommand(LocalDate.of(2020, 3, 20)).execute(modelStub);
        assertEquals(ScViewDayCommand.MESSAGE_NO_EVENT, commandResult.getFeedbackToUser());
    }

    @Test
    void execute_todayNoEvent_viewSuccessful() throws Exception {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        CommandResult commandResult = new ScViewDayCommand(LocalDate.now()).execute(modelStub);
        assertEquals(ScViewDayCommand.MESSAGE_NO_EVENT_TODAY, commandResult.getFeedbackToUser());
    }

    @Test
    void execute_validDateWithEvent_viewSuccessful() throws Exception {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        modelStub.addEvent(MEETING);
        CommandResult commandResult = new ScViewDayCommand(MEETING.getDate()).execute(modelStub);
        assertEquals("1. " + MEETING.toString() + "\n", commandResult.getFeedbackToUser());
    }

    @Test
    void execute_dateBeforeStart_throwsCommandException() {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        assertThrows(CommandException.class, () -> new ScViewDayCommand(SCHEDULE_START_DATE.minusDays(1))
                .execute(modelStub));
    }

    @Test
    void execute_dateAfterEnd_throwsCommandException() {

        ModelStubWithSchedule modelStub = new ModelStubWithSchedule();
        assertThrows(CommandException.class, () -> new ScViewDayCommand(SCHEDULE_END_DATE.plusDays(1))
                .execute(modelStub));
    }

    private class ModelStubWithSchedule extends ModelStub {

        private Schedule schedule = new Schedule(SCHEDULE_START_DATE, SCHEDULE_END_DATE);

        @Override
        public void addEvent(Event e) {

            schedule.addEvent(e);
        }

        @Override
        public boolean isWithinSem(LocalDate date) {

            return schedule.checkDateValidity(date);

        }

        @Override
        public String viewSchedule(LocalDate date) {

            return schedule.view(date);
        }
    }
}

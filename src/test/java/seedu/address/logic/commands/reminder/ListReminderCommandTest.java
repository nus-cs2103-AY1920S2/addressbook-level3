package seedu.address.logic.commands.reminder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderTAble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.reminders.ListReminderCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.student.StudentTAble;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListReminderCommand.
 */
public class ListReminderCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
                new TutorialTAble(), new ModTAble(), getTypicalReminderTAble());
        expectedModel = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
                new TutorialTAble(), new ModTAble(), model.getReminderTAble());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListReminderCommand(), model, ListReminderCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

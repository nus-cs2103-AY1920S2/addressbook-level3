package seedu.address.logic.commands.mods;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMods.getTypicalModTAble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.StudentTAble;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListModCommand.
 */
public class ListModCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), getTypicalModTAble(), new ReminderTAble());
        expectedModel = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), model.getModTAble(), new ReminderTAble());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListModCommand(), model, ListModCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

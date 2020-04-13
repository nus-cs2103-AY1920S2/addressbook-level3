package seedu.address.logic.commands.consult;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConsults.getTypicalConsultTAble;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.consults.ListConsultCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.ReminderTAble;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListConsultCommand.
 */
public class ListConsultCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), getTypicalConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());
        expectedModel = new ModelManager(model.getStudentTAble(), new UserPrefs(), model.getConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListConsultCommand(), model, ListConsultCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

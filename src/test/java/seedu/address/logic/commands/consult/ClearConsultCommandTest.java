package seedu.address.logic.commands.consult;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConsults.getTypicalConsultTAble;
import static seedu.address.testutil.TypicalStudents.getTypicalStudentTAble;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.consults.ClearConsultCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.ReminderTAble;

public class ClearConsultCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearConsultCommand(), model, ClearConsultCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), getTypicalConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());
        Model expectedModel = new ModelManager(getTypicalStudentTAble(), new UserPrefs(), getTypicalConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());

        expectedModel.setConsultTAble(new ConsultTAble());

        assertCommandSuccess(new ClearConsultCommand(), model, ClearConsultCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

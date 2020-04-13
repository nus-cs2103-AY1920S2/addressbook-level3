package seedu.address.logic.commands.mods;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMods.CS2103;
import static seedu.address.testutil.TypicalMods.getTypicalModTAble;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModCode;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.StudentTAble;

public class ViewModInfoCommandTest {

    private Model model = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
        new TutorialTAble(), getTypicalModTAble(), new ReminderTAble());

    @Test
    public void execute_modNotInList_throwsCommandException() {
        ViewModInfoCommand viewModInfoCommand = new ViewModInfoCommand(new ModCode("CS9999"));
        assertCommandFailure(viewModInfoCommand, model, MESSAGE_MISSING_MOD);
    }

    @Test
    public void execute_validModInList_success() {
        ViewModInfoCommand viewModInfoCommand = new ViewModInfoCommand(new ModCode(VALID_MODCODE));
        String expectedMessage = String.format(ViewModInfoCommand.MESSAGE_VIEW_MOD_SUCCESS, CS2103);

        Model expectedModel = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), model.getModTAble(), new ReminderTAble());
        expectedModel.setViewedMod(CS2103);

        assertCommandSuccess(viewModInfoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ViewModInfoCommand viewFirstCommand = new ViewModInfoCommand(new ModCode("AA1000"));
        ViewModInfoCommand viewSecondCommand = new ViewModInfoCommand(new ModCode("ZZ9999"));

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewModInfoCommand noteFirstCommandCopy = new ViewModInfoCommand(new ModCode("AA1000"));
        assertTrue(viewFirstCommand.equals(noteFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

}

package seedu.address.logic.commands.mods;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.mods.DeleteModCommand.MESSAGE_MOD_HAS_TUTORIAL;
import static seedu.address.testutil.TypicalMods.CS2103;
import static seedu.address.testutil.TypicalMods.getTypicalModTAble;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Location;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.event.tutorial.TutorialName;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.StudentTAble;

/**
 * Contains integration tests (interaction with the Model and Tutorials) and unit tests for
 * {@code DeleteModCommand}.
 */
public class DeleteModCommandTest {

    private Model model = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
        new TutorialTAble(), getTypicalModTAble(), new ReminderTAble());

    @Test
    public void execute_validModuleInList_success() {
        Mod modToDelete = model.findMod(new ModCode(VALID_MODCODE)).get();
        DeleteModCommand deleteModCommand = new DeleteModCommand(new ModCode(VALID_MODCODE));

        String expectedMessage = String.format(DeleteModCommand.MESSAGE_DELETE_MOD_SUCCESS, modToDelete);

        ModelManager expectedModel = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), model.getModTAble(), new ReminderTAble());
        expectedModel.deleteMod(modToDelete);

        assertCommandSuccess(deleteModCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_modNotInList_throwsCommandException() {
        DeleteModCommand deleteModCommand = new DeleteModCommand(new ModCode("CS9999"));
        assertCommandFailure(deleteModCommand, model, MESSAGE_MISSING_MOD);
    }

    @Test
    public void execute_validModInList_success() {
        DeleteModCommand deleteModCommand = new DeleteModCommand(new ModCode(VALID_MODCODE));
        String expectedMessage = String.format(DeleteModCommand.MESSAGE_DELETE_MOD_SUCCESS, CS2103);

        Model expectedModel = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), model.getModTAble(), new ReminderTAble());
        expectedModel.deleteMod(CS2103);

        assertCommandSuccess(deleteModCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_modHasTutorial_throwModHasTutorialException() {
        model.addTutorial(new Tutorial(new ModCode(VALID_MODCODE), new TutorialName("Sample"),
            DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.MIDNIGHT, new Location("Sample")));

        DeleteModCommand deleteModCommand = new DeleteModCommand(new ModCode(VALID_MODCODE));
        assertCommandFailure(deleteModCommand, model, MESSAGE_MOD_HAS_TUTORIAL);
    }

    @Test
    public void equals() {
        DeleteModCommand deleteFirstCommand = new DeleteModCommand(new ModCode("AA1000"));
        DeleteModCommand deleteSecondCommand = new DeleteModCommand(new ModCode("ZZ9999"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteModCommand deleteFirstCommandCopy = new DeleteModCommand(new ModCode("AA1000"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}

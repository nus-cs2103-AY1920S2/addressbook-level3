package seedu.address.logic.commands.mods;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNOTE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMods.CS2040C_ESSENTIALFILLED;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.testutil.ModBuilder;

class NoteModCommandTest {

    @Test
    public void constructor_nullField_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteModCommand(null, null));
    }

    @Test
    public void execute_validModLink_success() {
        Model model = new ModelManager();
        model.addMod(CS2040C_ESSENTIALFILLED);
        NoteModCommand noteModCommand = new NoteModCommand(new ModCode("CS2040C"),
            "Note changed");

        Mod expectedMod = new ModBuilder()
            .withModCode("CS2040C")
            .withModName("Data Structures and Algorithms")
            .withModNote("Note changed")
            .build();
        Model expectedModel = new ModelManager();
        expectedModel.addMod(expectedMod);

        assertCommandSuccess(noteModCommand, model,
            String.format(NoteModCommand.MESSAGE_EDIT_MODNOTE_SUCCESS, expectedMod), expectedModel);
    }

    @Test
    public void execute_missingMod_throwMissingModException() {
        NoteModCommand noteModCommand = new NoteModCommand(new ModCode(VALID_MODCODE),
            VALID_MODNOTE);
        Model model = new ModelManager();

        assertCommandFailure(noteModCommand, model, Messages.MESSAGE_MISSING_MOD);
    }

    @Test
    public void equals() {
        NoteModCommand noteFirstCommand = new NoteModCommand(new ModCode("AA1000"), "test");
        NoteModCommand noteSecondCommand = new NoteModCommand(new ModCode("ZZ9999"), "test");

        // same object -> returns true
        assertTrue(noteFirstCommand.equals(noteFirstCommand));

        // same values -> returns true
        NoteModCommand noteFirstCommandCopy = new NoteModCommand(new ModCode("AA1000"), "test");
        assertTrue(noteFirstCommand.equals(noteFirstCommandCopy));

        // different types -> returns false
        assertFalse(noteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(noteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(noteFirstCommand.equals(noteSecondCommand));
    }

}

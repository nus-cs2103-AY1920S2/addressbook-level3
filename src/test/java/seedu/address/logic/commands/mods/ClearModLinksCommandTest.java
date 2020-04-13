package seedu.address.logic.commands.mods;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNOTE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMods.CS2103;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.testutil.ModBuilder;

class ClearModLinksCommandTest {
    @Test
    public void execute_missingMod_throwsMissingModException() {
        Model model = new ModelManager();
        ClearModLinksCommand clearModLinksCommand = new ClearModLinksCommand(new ModCode(VALID_MODCODE));
        assertCommandFailure(clearModLinksCommand, model, Messages.MESSAGE_MISSING_MOD);
    }

    @Test
    public void execute_validModLinkList_success() {
        Model model = new ModelManager();
        model.addMod(CS2103);

        Mod cs2103Copy = new ModBuilder().withModCode(VALID_MODCODE)
            .withModName(VALID_MODNAME)
            .withModNote(VALID_MODNOTE)
            .build();
        Model expectedModel = new ModelManager();
        expectedModel.addMod(cs2103Copy);

        assertCommandSuccess(new ClearModLinksCommand(new ModCode(VALID_MODCODE)),
            model, String.format(ClearModLinksCommand.MESSAGE_SUCCESS, cs2103Copy), expectedModel);
    }

    @Test
    void testEquals() {
        ClearModLinksCommand cs2103Command = new ClearModLinksCommand(new ModCode("CS2103"));
        ClearModLinksCommand cs1010sCommand = new ClearModLinksCommand(new ModCode("CS1010S"));

        assertTrue(cs2103Command.equals(cs2103Command));
        assertFalse(cs2103Command.equals(cs1010sCommand));
        assertFalse(cs2103Command.equals(1));
    }
}

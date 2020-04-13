package seedu.address.logic.commands.mods;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK_NAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMods.CS2040C_ESSENTIALFILLED;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;
import seedu.address.testutil.ModBuilder;

class AddModLinkCommandTest {

    @Test
    public void constructor_nullField_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModLinkCommand(null, null, null));
    }

    @Test
    public void execute_validModLink_success() {
        Model model = new ModelManager();
        model.addMod(CS2040C_ESSENTIALFILLED);
        AddModLinkCommand addModLinkCommand = new AddModLinkCommand(new ModCode("CS2040C"),
            "Module Website", new ModLink("https://www.comp.nus.edu.sg/~stevenha/cs2040c.html"));

        Mod expectedMod = new ModBuilder()
            .withModCode("CS2040C")
            .withModName("Data Structures and Algorithms")
            .addModLinkPair("Module Website",
                "https://www.comp.nus.edu.sg/~stevenha/cs2040c.html")
            .build();
        Model expectedModel = new ModelManager();
        expectedModel.addMod(expectedMod);

        assertCommandSuccess(addModLinkCommand, model,
            String.format(AddModLinkCommand.MESSAGE_SUCCESS, expectedMod), expectedModel);
    }

    @Test
    public void execute_missingMod_throwMissingModException() {
        AddModLinkCommand addModLinkCommand = new AddModLinkCommand(new ModCode(VALID_MODCODE),
            VALID_MODLINK_NAME, new ModLink(VALID_MODLINK));
        Model model = new ModelManager();

        assertCommandFailure(addModLinkCommand, model, Messages.MESSAGE_MISSING_MOD);
    }

}

package seedu.address.model.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNOTE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMods.CS2040C_ESSENTIALFILLED;
import static seedu.address.testutil.TypicalMods.CS2103;

import java.util.List;

import org.junit.jupiter.api.Test;

class ModTest {
    @Test
    public void constructor_nullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Mod(new ModCode(VALID_MODCODE), null));
        assertThrows(NullPointerException.class, () -> new Mod((Mod) null, VALID_MODNAME));
        assertThrows(NullPointerException.class, () -> new Mod(CS2040C_ESSENTIALFILLED, null));
        assertThrows(NullPointerException.class, () -> new Mod(CS2040C_ESSENTIALFILLED, VALID_MODLINK_NAME, null));
    }

    @Test
    void getModCode_validMod_correctModCode() {
        assertEquals(CS2103.getModCode(), new ModCode(VALID_MODCODE));
    }

    @Test
    void getModName_validMod_correctModName() {
        assertEquals(CS2103.getModName(), VALID_MODNAME);
    }

    @Test
    void getNote_validMod_correctNote() {
        assertEquals(CS2103.getNote(), VALID_MODNOTE);
    }

    @Test
    void getLinks_validMod_correctLinks() {
        List<ModLinkPair> links = CS2103.getLinks();
        assertEquals(1, links.size());
        ModLinkPair modLinkPair = links.get(0);

        ModLink modLink = new ModLink(VALID_MODLINK);
        ModLinkPair expected = new ModLinkPair(VALID_MODLINK_NAME, modLink);
        assertEquals(expected, modLinkPair);
    }

    @Test
    void clearLinks_validMod_noLinks() {
        Mod noLinksCs2103 = CS2103.clearLinks();
        assertEquals(0, noLinksCs2103.getLinks().size());
    }

    @Test
    void isSameMod_validSimilarMod_returnTrue() {
        Mod stubCs2103 = new Mod(new ModCode("CS2103"), "Default Name");
        assertTrue(CS2103.isSameMod(stubCs2103));
        assertTrue(CS2103.isSameMod(CS2103));
    }

    @Test
    void testEquals() {
        assertTrue(CS2103.equals(CS2103));
        assertFalse(CS2103.equals(null));
        Mod expectedCS2103 = new Mod(
            new Mod(
            new Mod(new ModCode("CS2103"), "SOFTWARE ENGINEERING"), "SE is fun!!!"),
                "Module Website", new ModLink("http://www.comp.nus.edu.sg/~cs2103"));
        assertEquals(expectedCS2103, CS2103);
    }

    @Test
    void testToString() {
        assertEquals(CS2103.toString(),
            "CS2103 Name: SOFTWARE ENGINEERING Notes: SE is fun!!! "
                + "Links: Module Website=http://www.comp.nus.edu.sg/~cs2103; ");
    }
}

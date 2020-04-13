package seedu.address.model.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK_NAME;

import org.junit.jupiter.api.Test;

class ModLinkPairTest {

    @Test
    void getKey() {
        ModLinkPair modLinkPair = new ModLinkPair(VALID_MODLINK_NAME, new ModLink(VALID_MODLINK));
        assertEquals(VALID_MODLINK_NAME, modLinkPair.getKey());
    }

    @Test
    void getValue() {
        ModLinkPair modLinkPair = new ModLinkPair(VALID_MODLINK_NAME, new ModLink(VALID_MODLINK));
        assertEquals(new ModLink(VALID_MODLINK), modLinkPair.getValue());
    }

    @Test
    void testEquals() {
        ModLinkPair modLinkPair = new ModLinkPair(VALID_MODLINK_NAME, new ModLink(VALID_MODLINK));
        assertTrue(modLinkPair.equals(modLinkPair));
        assertFalse(modLinkPair.equals(null));

        ModLinkPair equalPair = new ModLinkPair("Module Website",
            new ModLink("http://www.comp.nus.edu.sg/~cs2103"));
        assertEquals(equalPair, modLinkPair);
    }
}

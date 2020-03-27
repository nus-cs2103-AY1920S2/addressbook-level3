package seedu.address.model;

import org.junit.jupiter.api.Test;
import seedu.address.model.good.Good;
import seedu.address.testutil.GoodBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGoods.APPLE;
import static seedu.address.testutil.TypicalGoods.BANANA;
import static seedu.address.testutil.TypicalGoods.CITRUS;
import static seedu.address.testutil.TypicalGoods.DURIAN;

public class VersionedInventoryTest {

    private VersionedInventory versionedInventory = new VersionedInventory();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> versionedInventory.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        versionedInventory.addGood(APPLE);
        versionedInventory.commit();
        versionedInventory.undo();
        assertFalse(versionedInventory.hasGood(APPLE));
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedInventory.addGood(APPLE);
        versionedInventory.commit();
        versionedInventory.addGood(BANANA);
        versionedInventory.commit();
        versionedInventory.addGood(CITRUS);
        versionedInventory.commit();

        // first undo goes to second commit with apple and banana
        versionedInventory.undo();
        assertTrue(versionedInventory.hasGood(APPLE));
        assertTrue(versionedInventory.hasGood(BANANA));
        assertFalse(versionedInventory.hasGood(CITRUS));

        // second undo goes to first commit with apple only
        versionedInventory.undo();
        assertTrue(versionedInventory.hasGood(APPLE));
        assertFalse(versionedInventory.hasGood(BANANA));
        assertFalse(versionedInventory.hasGood(CITRUS));
    }

    @Test
    public void undo_afterUnsavedChanges_removesUnsavedAndPreviousChanges() {
        versionedInventory.addGood(APPLE);
        versionedInventory.commit();

        Good g = new GoodBuilder().withGoodName("Erased Ignored").build();
        versionedInventory.undo();
        assertFalse(versionedInventory.hasGood(APPLE));
        assertFalse(versionedInventory.hasGood(g));
    }

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        versionedInventory.addGood(APPLE);
        versionedInventory.commit();
        versionedInventory.addGood(BANANA);
        versionedInventory.commit();
        versionedInventory.addGood(CITRUS);
        versionedInventory.commit();

        // ensures the current state points to the most recent commit
        versionedInventory.undo();
        versionedInventory.addGood(DURIAN);
        assertTrue(versionedInventory.hasGood(DURIAN));

        // ensures that current state is not added on top of deleted history
        versionedInventory.undo();
        assertFalse(versionedInventory.hasGood(DURIAN));
        assertFalse(versionedInventory.hasGood(CITRUS));
    }
}


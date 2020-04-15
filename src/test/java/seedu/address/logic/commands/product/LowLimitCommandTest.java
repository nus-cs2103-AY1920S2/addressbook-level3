package seedu.address.logic.commands.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.THRESHOLD_BAG;
import static seedu.address.logic.commands.CommandTestUtil.THRESHOLD_WATCH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRODUCT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRODUCT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.util.QuantityThreshold;

public class LowLimitCommandTest {
    @Test
    public void constructor_nullLowLimit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LowLimitCommand(null, null));
    }

    @Test
    public void constructor_negativeLowLimit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new LowLimitCommand(
                new Index(0), new QuantityThreshold("-1")));
    }

    @Test
    public void constructor_negativeIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> new LowLimitCommand(
                new Index(-1), new QuantityThreshold("10")));
    }

    @Test
    public void equals() {
        final LowLimitCommand standardCommand = new LowLimitCommand(INDEX_FIRST_PRODUCT, THRESHOLD_BAG);

        // same values -> returns true
        QuantityThreshold copyThreshold = new QuantityThreshold(THRESHOLD_BAG.value);
        LowLimitCommand commandWithSameValues = new LowLimitCommand(INDEX_FIRST_PRODUCT, copyThreshold);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearProductCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new LowLimitCommand(INDEX_SECOND_PRODUCT, THRESHOLD_BAG)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new LowLimitCommand(INDEX_FIRST_PRODUCT, THRESHOLD_WATCH)));
    }
}

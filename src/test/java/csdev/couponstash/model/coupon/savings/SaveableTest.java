package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Saveable.
 */
public class SaveableTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Saveable(null));
    }

    @Test
    public void constructor_invalidSaveable_throwsIllegalArgumentException() {
        // negative count
        assertThrows(IllegalArgumentException.class, () -> new Saveable("Coconut", -1));
        // zero count
        assertThrows(IllegalArgumentException.class, () -> new Saveable("Coconut", 0));
        // empty String
        assertThrows(IllegalArgumentException.class, () -> new Saveable(""));
        // String too long
        assertThrows(IllegalArgumentException.class, () -> new Saveable("BrattbyBrattbyBrattby"));
    }

    @Test
    public void increaseCount_sameSaveable_works() {
        assertEquals(new Saveable("Rambutan").increaseCount(new Saveable("Rambutan", 4)), new Saveable("Rambutan", 5));
    }

    @Test
    public void increaseCount_differentSaveable_remainsAtSameValue() {
        assertEquals(new Saveable("Rambutan").increaseCount(new Saveable("Pineapple", 4)), new Saveable("Rambutan", 1));
    }

    @Test
    public void increaseCount_integerOverflow_remainsAtSameValue() {
        assertEquals(new Saveable("Rambutan", Integer.MAX_VALUE).increaseCount(1),
                new Saveable("Rambutan", Integer.MAX_VALUE));
    }

    @Test
    public void isValidSaveableValue() {
        assertFalse(Saveable.isValidSaveableValue(null, 1)); // null
        assertFalse(Saveable.isValidSaveableValue("", 1)); // empty String
        assertFalse(Saveable.isValidSaveableValue("Watermelon", 0)); // non-positive count
        assertFalse(Saveable.isValidSaveableValue("CoconutCoconutCoconut", 1)); // String too long

        // valid monetaryAmount
        assertTrue(Saveable.isValidSaveableValue("CoconutCoconutDurian", 10));
    }
}

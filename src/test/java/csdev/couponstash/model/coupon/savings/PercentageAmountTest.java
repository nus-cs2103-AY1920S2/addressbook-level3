package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for PercentageAmount.
 */
public class PercentageAmountTest {

    @Test
    public void constructor_invalidPercentageAmount_throwsIllegalArgumentException() {
        // negative
        assertThrows(IllegalArgumentException.class, () -> new PercentageAmount(-1));
        // more than 100
        assertThrows(IllegalArgumentException.class, () -> new PercentageAmount(100.1));
    }

    @Test
    public void isValidPercentage() {
        // invalid percentageAmount
        assertFalse(PercentageAmount.isValidPercentage(-0.05)); // negative
        assertFalse(PercentageAmount.isValidPercentage(105)); // more than 100

        // valid percentageAmount
        assertTrue(PercentageAmount.isValidPercentage(5));
    }
}

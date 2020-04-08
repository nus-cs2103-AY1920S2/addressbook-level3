package csdev.couponstash.model.coupon.savings;

import org.junit.jupiter.api.Test;

import static csdev.couponstash.testutil.Assert.assertThrows;

/**
 * Unit test for Saveable.
 */
public class SaveableTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Saveable(null));
    }
}

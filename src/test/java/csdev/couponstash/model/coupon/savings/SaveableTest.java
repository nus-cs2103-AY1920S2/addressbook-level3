package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Saveable.
 */
public class SaveableTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Saveable(null));
    }
}

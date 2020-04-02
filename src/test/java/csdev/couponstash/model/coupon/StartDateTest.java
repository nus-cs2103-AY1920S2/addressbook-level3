package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class StartDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    @Test
    public void constructor_invalidStartDate_throwsIllegalArgumentException() {
        String invalidStartDate = " ";
        assertThrows(IllegalArgumentException.class, () -> new StartDate(invalidStartDate));
    }
}

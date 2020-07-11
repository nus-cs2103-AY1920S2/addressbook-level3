package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PromoCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PromoCode(null));
    }

}

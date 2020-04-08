package csdev.couponstash.model.coupon.savings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for PureMonetarySavings.
 */
public class PureMonetarySavingsTest {

    @Test
    public void copy_validPureMonetarySavings_copiesAreEqual() {
        PureMonetarySavings pms = new PureMonetarySavings(
                new MonetaryAmount(3, 4),
                Arrays.asList(new Saveable("France", 2), new Saveable("Italy", 4))
        );
        assertEquals(pms.copy(), pms);
    }
}

package csdev.couponstash.model.coupon.savings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for PureMonetarySavings. Also acts as integration tests
 * between MonetaryAmount, Saveable and PureMonetarySavings.
 */
public class PureMonetarySavingsTest {

    @Test
    public void add_validPureMonetarySavings_combinesNumericalAmountAndSaveables() {
        PureMonetarySavings initialPms = new PureMonetarySavings(
                new MonetaryAmount(282, 45),
                Arrays.asList(new Saveable("Belgium", 3), new Saveable("Netherlands", 14),
                        new Saveable("Germany", 11))
        );
        PureMonetarySavings pmsToBeAdded = new PureMonetarySavings(
                new MonetaryAmount(13, 78),
                Arrays.asList(new Saveable("Netherlands", 2), new Saveable("Czechia", 8),
                        new Saveable("Belgium"))
        );
        PureMonetarySavings expectedResult = new PureMonetarySavings(
                new MonetaryAmount(296, 23),
                Arrays.asList(new Saveable("Belgium", 4), new Saveable("Netherlands", 16),
                        new Saveable("Germany", 11), new Saveable("Czechia", 8))
        );
        assertEquals(initialPms.add(pmsToBeAdded), expectedResult);
    }

    @Test
    public void copy_validPureMonetarySavings_copiesAreEqual() {
        PureMonetarySavings pms = new PureMonetarySavings(
                new MonetaryAmount(3, 4),
                Arrays.asList(new Saveable("France", 2), new Saveable("Italy", 4))
        );
        assertEquals(pms.copy(), pms);
    }
}

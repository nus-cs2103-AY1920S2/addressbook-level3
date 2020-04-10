package team.easytravel.model.trip;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.testutil.Assert;

public class ExchangeRateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExchangeRate(null));
    }

    @Test
    public void constructor_invalidExchangeRate_throwsIllegalArgumentException() {
        Double invalidBudget = -0.000000000000000000000000001; //this is illegal
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExchangeRate(invalidBudget));
    }

    @Test
    public void isValidExchangeRate() {
        // null exchangerate
        Assert.assertThrows(NullPointerException.class, () -> ExchangeRate.isValidExchangeRate(null));

        // invalid Exchange Rate
        assertFalse(ExchangeRate.isValidExchangeRate(000000.0)); //  0s
        assertFalse(ExchangeRate.isValidExchangeRate(-000000.0)); // negative 0s
        assertFalse(ExchangeRate.isValidExchangeRate(-2.0)); // is negative
        assertFalse(ExchangeRate.isValidExchangeRate(999999999.0)); // very big number
        assertFalse(ExchangeRate.isValidExchangeRate(101.0)); // exactly 101


        // valid ExchangeRate
        assertTrue(ExchangeRate.isValidExchangeRate(000000.1)); //  trailing 0 at front
        assertTrue(ExchangeRate.isValidExchangeRate(100.0)); //  exactly at max
        assertTrue(ExchangeRate.isValidExchangeRate(0.0001)); //extra 0s
        assertTrue(ExchangeRate.isValidExchangeRate(0.00000000001)); //very small
        assertTrue(ExchangeRate.isValidExchangeRate(0.100000000000)); //trailing 0 at back

    }
}

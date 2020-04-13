package seedu.address.commons.core.fraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.math.BigDecimal;

import org.apache.commons.math3.exception.NullArgumentException;
import org.junit.jupiter.api.Test;

public class MixedFractionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MixedFraction(null));
    }

    @Test
    public void parseUnsignedMixedFraction() {
        // null input
        assertThrows(NumberFormatException.class, () -> MixedFraction.parseUnsignedMixedFraction(null));

        // invalid input
        assertThrows(NumberFormatException.class, () -> MixedFraction.parseUnsignedMixedFraction(""));
        assertThrows(NumberFormatException.class, () -> MixedFraction.parseUnsignedMixedFraction("null"));
        assertThrows(NumberFormatException.class, () -> MixedFraction.parseUnsignedMixedFraction("1"));
        assertThrows(NumberFormatException.class, () -> MixedFraction.parseUnsignedMixedFraction("1 / 2"));
        assertThrows(NumberFormatException.class, () -> MixedFraction.parseUnsignedMixedFraction("0.5"));

        // valid input
        assertEquals(0, MixedFraction.parseUnsignedMixedFraction("1/2").intValue());
        assertEquals(1, MixedFraction.parseUnsignedMixedFraction("1/2").getNumerator());
        assertEquals(2, MixedFraction.parseUnsignedMixedFraction("1/2").getDenominator());
        assertEquals(1, MixedFraction.parseUnsignedMixedFraction("1 2/3").intValue());
        assertEquals(5, MixedFraction.parseUnsignedMixedFraction("1 2/3").getNumerator());
        assertEquals(3, MixedFraction.parseUnsignedMixedFraction("1 2/3").getDenominator());
    }

    @Test
    public void getFromBigDecimal() {
        // integer
        assertEquals(1, MixedFraction.getFromBigDecimal(new BigDecimal("1")).intValue());
        assertEquals(1, MixedFraction.getFromBigDecimal(new BigDecimal("1")).getNumerator());
        assertEquals(1, MixedFraction.getFromBigDecimal(new BigDecimal("1")).getDenominator());

        // double
        assertEquals(1, MixedFraction.getFromBigDecimal(new BigDecimal("1.2")).intValue());
        assertEquals(6, MixedFraction.getFromBigDecimal(new BigDecimal("1.2")).getNumerator());
        assertEquals(5, MixedFraction.getFromBigDecimal(new BigDecimal("1.2")).getDenominator());

        // negative double
        assertEquals(-1, MixedFraction.getFromBigDecimal(new BigDecimal("-1.2")).intValue());
        assertEquals(-6, MixedFraction.getFromBigDecimal(new BigDecimal("-1.2")).getNumerator());
        assertEquals(5, MixedFraction.getFromBigDecimal(new BigDecimal("-1.2")).getDenominator());
    }

    @Test
    public void add() {
        MixedFraction a = new MixedFraction(3, 2);
        MixedFraction b = new MixedFraction(4, 3);
        MixedFraction c = new MixedFraction(-5, 2);

        // null input
        assertThrows(NullArgumentException.class, () -> a.add(null));

        // valid input
        assertEquals("3", a.add(a).toString());
        assertEquals("2 5/6", a.add(b).toString());
        assertEquals("-1", a.add(c).toString());

        assertEquals("2 5/6", b.add(a).toString());
        assertEquals("2 2/3", b.add(b).toString());
        assertEquals("-1 1/6", b.add(c).toString());

        assertEquals("-1", c.add(a).toString());
        assertEquals("-1 1/6", c.add(b).toString());
        assertEquals("-5", c.add(c).toString());
    }

    @Test
    public void subtract() {
        MixedFraction a = new MixedFraction(3, 2);
        MixedFraction b = new MixedFraction(4, 3);
        MixedFraction c = new MixedFraction(-5, 2);

        // null input
        assertThrows(NullArgumentException.class, () -> a.subtract(null));

        // valid input
        assertEquals("0", a.subtract(a).toString());
        assertEquals("1/6", a.subtract(b).toString());
        assertEquals("4", a.subtract(c).toString());

        assertEquals("-1/6", b.subtract(a).toString());
        assertEquals("0", b.subtract(b).toString());
        assertEquals("3 5/6", b.subtract(c).toString());

        assertEquals("-4", c.subtract(a).toString());
        assertEquals("-3 5/6", c.subtract(b).toString());
        assertEquals("0", c.subtract(c).toString());
    }

    @Test
    public void divide() {
        MixedFraction a = new MixedFraction(3, 2);
        MixedFraction b = new MixedFraction(4, 3);
        MixedFraction c = new MixedFraction(-5, 2);

        // null input
        assertThrows(NullArgumentException.class, () -> a.subtract(null));

        // valid input
        assertEquals("1", a.divide(a).toString());
        assertEquals("1 1/8", a.divide(b).toString());
        assertEquals("-3/5", a.divide(c).toString());

        assertEquals("8/9", b.divide(a).toString());
        assertEquals("1", b.divide(b).toString());
        assertEquals("-8/15", b.divide(c).toString());

        assertEquals("-1 2/3", c.divide(a).toString());
        assertEquals("-1 7/8", c.divide(b).toString());
        assertEquals("1", c.divide(c).toString());
    }
}

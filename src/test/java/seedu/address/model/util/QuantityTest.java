package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.product.Price;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        assertThrows(IllegalArgumentException.class, () -> new Price(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity
        assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(null));

        // invalid quantities
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("price")); // non-numeric
        assertFalse(Quantity.isValidQuantity("9011p041")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("9312 1534")); // spaces within digits
        assertFalse(Quantity.isValidQuantity("93121534")); // exceed max value

        // valid quantities
        assertTrue(Quantity.isValidQuantity("911")); // exactly 3 numbers
        assertTrue(Quantity.isValidQuantity("1000000")); // long prices
    }

    @Test
    public void equals() {
        //same value -> returns true
        Quantity firstQuantity = new Quantity(10);
        Quantity secondQuantity = new Quantity(10);
        assertTrue(firstQuantity.equals(secondQuantity));

        //same object -> returns false
        Quantity quantity = new Quantity(10);
        assertTrue(quantity.equals(quantity));

        //compared with null -> throws exception
        assertThrows(NullPointerException.class, () -> quantity.compareTo(null));

        //different value -> returns false
        firstQuantity = new Quantity(11);
        secondQuantity = new Quantity(1);
        assertFalse(firstQuantity.equals(secondQuantity));
    }

    @Test
    public void minus() {
        //minus null -> throws null pointer exception
        Quantity five = new Quantity(5);
        assertThrows(NullPointerException.class, () -> five.plus(null));

        //self minus self -> return 0
        Quantity zero = new Quantity(0);
        assertTrue(five.minus(five).equals(zero));

        //self minus number larger than self -> throws exception
        Quantity six = new Quantity(6);
        assertThrows(IllegalArgumentException.class, () -> five.minus(six));

        //self minus number smaller than self -> return normal result
        Quantity one = new Quantity(1);
        assertTrue(six.minus(five).equals(one));
    }

    @Test
    public void plus() {
        //plus null -> throws exception
        Quantity five = new Quantity(5);
        assertThrows(NullPointerException.class, () -> five.plus(null));

        //self minus self -> return self x 2
        Quantity ten = new Quantity(10);
        assertTrue(five.plus(five).equals(ten));

        //self plus other number -> returns quantity with value = self + other
        Quantity six = new Quantity(6);
        Quantity eleven = new Quantity(11);
        assertTrue(five.plus(six).equals(eleven));
    }
}


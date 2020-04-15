package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TransactionQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionQuantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionQuantity(invalidQuantity));
    }

    @Test
    public void isValidFormat() {
        // null quantity
        assertThrows(NullPointerException.class, () -> TransactionQuantity.isValidFormat(null));

        // invalid quantities
        assertFalse(TransactionQuantity.isValidFormat("")); // empty string
        assertFalse(TransactionQuantity.isValidFormat(" ")); // spaces only
        assertFalse(TransactionQuantity.isValidFormat("0")); // smaller than minimum value
        assertFalse(TransactionQuantity.isValidFormat("price")); // non-numeric
        assertFalse(TransactionQuantity.isValidFormat("9011p041")); // alphabets within digits
        assertFalse(TransactionQuantity.isValidFormat("9312 1534")); // spaces within digits
        assertFalse(TransactionQuantity.isValidFormat("93121534")); // exceed max value

        // valid quantities
        assertTrue(TransactionQuantity.isValidFormat("1")); // minimal valid number
        assertTrue(TransactionQuantity.isValidFormat("911")); // exactly 3 numbers
        assertTrue(TransactionQuantity.isValidFormat("1000000")); // long prices
    }

    @Test
    public void isValidValue() {
        // invalid values
        assertFalse(TransactionQuantity.isValidValue(0)); // smaller than minimal
        assertFalse(TransactionQuantity.isValidValue(1000001)); // exceed max value
        assertFalse(TransactionQuantity.isValidValue(2000001)); // exceed max value

        // valid values
        assertTrue(TransactionQuantity.isValidValue(1)); // minimal valid number
        assertTrue(TransactionQuantity.isValidValue(999999)); // maximal valid number
        assertTrue(TransactionQuantity.isValidValue(10)); // two digits
        assertTrue(TransactionQuantity.isValidValue(143)); // three digits
    }

    @Test
    public void equals() {
        //same value -> returns true
        TransactionQuantity firstQuantity = new TransactionQuantity(10);
        TransactionQuantity secondQuantity = new TransactionQuantity(10);
        assertTrue(firstQuantity.equals(secondQuantity));

        //same object -> returns false
        TransactionQuantity quantity = new TransactionQuantity(10);
        assertTrue(quantity.equals(quantity));

        //compared with null -> throws exception
        assertThrows(NullPointerException.class, () -> quantity.compareTo(null));

        //different value -> returns false
        firstQuantity = new TransactionQuantity(11);
        secondQuantity = new TransactionQuantity(1);
        assertFalse(firstQuantity.equals(secondQuantity));
    }

    @Test
    public void minus() {
        //minus null -> throws null pointer exception
        TransactionQuantity five = new TransactionQuantity(5);
        assertThrows(NullPointerException.class, () -> five.plus(null));

        //self minus number larger than self -> throws exception
        TransactionQuantity six = new TransactionQuantity(6);
        assertThrows(IllegalArgumentException.class, () -> five.minus(six));

        //self minus number smaller than self -> return normal result
        TransactionQuantity one = new TransactionQuantity(1);
        assertTrue(six.minus(five).equals(one));
    }

    @Test
    public void plus() {
        //plus null -> throws exception
        TransactionQuantity five = new TransactionQuantity(5);
        assertThrows(NullPointerException.class, () -> five.plus(null));

        //self minus self -> return self x 2
        TransactionQuantity ten = new TransactionQuantity(10);
        assertTrue(five.plus(five).equals(ten));

        //self plus other number -> returns quantity with value = self + other
        TransactionQuantity six = new TransactionQuantity(6);
        TransactionQuantity eleven = new TransactionQuantity(11);
        assertTrue(five.plus(six).equals(eleven));
    }
}


package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProductQuantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        assertThrows(IllegalArgumentException.class, () -> new ProductQuantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity
        assertThrows(NullPointerException.class, () -> ProductQuantity.isValidFormat(null));

        // invalid quantities
        assertFalse(ProductQuantity.isValidFormat("")); // empty string
        assertFalse(ProductQuantity.isValidFormat(" ")); // spaces only
        assertFalse(ProductQuantity.isValidFormat("price")); // non-numeric
        assertFalse(ProductQuantity.isValidFormat("9011p041")); // alphabets within digits
        assertFalse(ProductQuantity.isValidFormat("9312 1534")); // spaces within digits
        assertFalse(ProductQuantity.isValidFormat("93121534")); // exceed max value

        // valid quantities
        assertTrue(ProductQuantity.isValidFormat("911")); // exactly 3 numbers
        assertTrue(ProductQuantity.isValidFormat("1000000")); // long prices
    }

    @Test
    public void equals() {
        //same value -> returns true
        ProductQuantity firstQuantity = new ProductQuantity(10);
        ProductQuantity secondQuantity = new ProductQuantity(10);
        assertTrue(firstQuantity.equals(secondQuantity));

        //same object -> returns false
        ProductQuantity quantity = new ProductQuantity(10);
        assertTrue(quantity.equals(quantity));

        //compared with null -> throws exception
        assertThrows(NullPointerException.class, () -> quantity.compareTo(null));

        //different value -> returns false
        firstQuantity = new ProductQuantity(11);
        secondQuantity = new ProductQuantity(1);
        assertFalse(firstQuantity.equals(secondQuantity));
    }

    @Test
    public void minus() {
        //minus null -> throws null pointer exception
        ProductQuantity five = new ProductQuantity(5);
        assertThrows(NullPointerException.class, () -> five.plus(null));

        //self minus self -> return 0
        ProductQuantity zero = new ProductQuantity(0);
        assertTrue(five.minus(five).equals(zero));

        //self minus number larger than self -> throws exception
        ProductQuantity six = new ProductQuantity(6);
        assertThrows(IllegalArgumentException.class, () -> five.minus(six));

        //self minus number smaller than self -> return normal result
        ProductQuantity one = new ProductQuantity(1);
        assertTrue(six.minus(five).equals(one));
    }

    @Test
    public void plus() {
        //plus null -> throws exception
        ProductQuantity five = new ProductQuantity(5);
        assertThrows(NullPointerException.class, () -> five.plus(null));

        //self minus self -> return self x 2
        ProductQuantity ten = new ProductQuantity(10);
        assertTrue(five.plus(five).equals(ten));

        //self plus other number -> returns quantity with value = self + other
        ProductQuantity six = new ProductQuantity(6);
        ProductQuantity eleven = new ProductQuantity(11);
        assertTrue(five.plus(six).equals(eleven));
    }
}


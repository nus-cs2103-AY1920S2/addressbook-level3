package team.easytravel.model.listmanagers.packinglistitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.testutil.Assert;

class QuantityTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructorInvalidQuantityThrowsIllegalArgumentException() {
        Integer integer = (Integer) 0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(integer));
    }

    @Test
    public void isValidQuantity() {

        // null quantity
        Integer integer = (Integer) null;
        Assert.assertThrows(NullPointerException.class, () -> Quantity.isValidQuantity(integer));

        // invalid quantity
        assertFalse(Quantity.isValidQuantity(0)); // 0 quantity.
        assertFalse(Quantity.isValidQuantity(-100)); //Negative quantity
        assertFalse(Quantity.isValidQuantity(3 / 4)); //Fraction Quantity

        // Valid quantity
        assertTrue(Quantity.isValidQuantity(4)); //Normal case
        assertTrue(Quantity.isValidQuantity(18)); //Normal case
        assertTrue(Quantity.isValidQuantity(888)); //Normal case
        assertTrue(Quantity.isValidQuantity(1000)); //Normal case
        assertTrue(Quantity.isValidQuantity(99999)); //Normal case
    }

    @Test
    public void testToString() {

        assertEquals("10", new Quantity(10).toString());

        //Wrong toString
        assertNotEquals("10", new Quantity(18).toString());

    }

    @Test
    public void testEquals() {

        //Equal Description Object
        assertEquals(new Quantity(18), new Quantity(18)); // When two quantities are the same.

        //Non Equal Description Object
        assertNotEquals(new Quantity(4), new Quantity(23)); // When two quantities are different.

    }

    @Test
    public void testHashCode() {

        // Equal Hashcode
        assertEquals(new Quantity(1).hashCode(), new Quantity(1).hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Quantity(15).hashCode(), new Quantity(1).hashCode());


    }
}

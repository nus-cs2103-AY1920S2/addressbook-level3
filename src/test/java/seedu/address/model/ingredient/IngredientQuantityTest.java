package seedu.address.model.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.ingredient.exceptions.NonPositiveIngredientQuantityException;

public class IngredientQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IngredientQuantity(null));
    }

    @Test
    public void constructor_invalidIngredientQuantity_throwsIllegalArgumentException() {
        String invalidIngredientQuantity = "";
        assertThrows(IllegalArgumentException.class, () -> new IngredientQuantity(invalidIngredientQuantity));
    }

    @Test
    public void isValidIngredientQuantity() {
        // null ingredient quantity
        assertThrows(NullPointerException.class, () -> IngredientQuantity.isValidIngredientQuantity(null));

        // invalid ingredient quantity
        assertFalse(IngredientQuantity.isValidIngredientQuantity("")); // empty string
        assertFalse(IngredientQuantity.isValidIngredientQuantity(" ")); // spaces only
        assertFalse(IngredientQuantity.isValidIngredientQuantity("dozen")); // starts with alphabets
        assertFalse(IngredientQuantity.isValidIngredientQuantity("1.")); // whole number with decimal point
        assertFalse(IngredientQuantity.isValidIngredientQuantity("1 / 2 cups")); // spaces in fraction
        assertFalse(IngredientQuantity.isValidIngredientQuantity("1 piece1")); // number in unit
        assertFalse(IngredientQuantity.isValidIngredientQuantity("01 g")); // leading zero
        assertFalse(IngredientQuantity.isValidIngredientQuantity("0 g")); // zero whole number
        assertFalse(IngredientQuantity.isValidIngredientQuantity("0.0 g")); // zero decimal
        assertFalse(IngredientQuantity.isValidIngredientQuantity(".0 g")); // zero value
        assertFalse(IngredientQuantity.isValidIngredientQuantity("0/1 g")); // zero numerator
        assertFalse(IngredientQuantity.isValidIngredientQuantity("1/0 g")); // zero denominator
        assertFalse(IngredientQuantity.isValidIngredientQuantity("0 1/2 g")); // zero whole part
        assertFalse(IngredientQuantity.isValidIngredientQuantity("-1.2 g")); // negative decimal
        assertFalse(IngredientQuantity.isValidIngredientQuantity("-1/2 g")); // negative fraction

        // valid ingredient quantity
        assertTrue(IngredientQuantity.isValidIngredientQuantity("12345")); // whole number
        assertTrue(IngredientQuantity.isValidIngredientQuantity("0.5")); // decimal number
        assertTrue(IngredientQuantity.isValidIngredientQuantity("1/2")); // pure fraction
        assertTrue(IngredientQuantity.isValidIngredientQuantity("1 1/2")); // mixed fraction
        assertTrue(IngredientQuantity.isValidIngredientQuantity("100 ml")); // whole number and unit
        assertTrue(IngredientQuantity.isValidIngredientQuantity("100ml")); // no space between value and unit
        assertTrue(IngredientQuantity.isValidIngredientQuantity("2.5 cups")); // decimal number and unit
        assertTrue(IngredientQuantity.isValidIngredientQuantity(".5 cups")); // starts with decimal point
        assertTrue(IngredientQuantity.isValidIngredientQuantity("1/2 cups")); // pure fractions and unit
        assertTrue(IngredientQuantity.isValidIngredientQuantity("2 1/2 cups")); // mixed fractions and unit
        assertTrue(IngredientQuantity.isValidIngredientQuantity("1 Tablespoon")); // with capital letters
        assertTrue(IngredientQuantity.isValidIngredientQuantity("1 rounded tsp")); // unit with spaces
    }

    @Test
    public void hasSameUnitAs() {
        IngredientQuantity a = new IngredientQuantity("1 cup");
        IngredientQuantity b = new IngredientQuantity("0.25 cup");
        IngredientQuantity c = new IngredientQuantity("1/2 cup");
        IngredientQuantity d = new IngredientQuantity("100 ml");

        assertTrue(a.hasSameUnitAs(a));
        assertTrue(a.hasSameUnitAs(b));
        assertTrue(a.hasSameUnitAs(c));
        assertTrue(b.hasSameUnitAs(a));
        assertTrue(b.hasSameUnitAs(b));
        assertTrue(b.hasSameUnitAs(c));
        assertTrue(c.hasSameUnitAs(a));
        assertTrue(c.hasSameUnitAs(b));
        assertTrue(c.hasSameUnitAs(c));
        assertTrue(d.hasSameUnitAs(d));
        assertFalse(a.hasSameUnitAs(d));
        assertFalse(b.hasSameUnitAs(d));
        assertFalse(c.hasSameUnitAs(d));
        assertFalse(d.hasSameUnitAs(a));
        assertFalse(d.hasSameUnitAs(b));
        assertFalse(d.hasSameUnitAs(c));
    }

    @Test
    public void add() {
        IngredientQuantity a = new IngredientQuantity("1 cup");
        IngredientQuantity b = new IngredientQuantity("0.25 cup");
        IngredientQuantity c = new IngredientQuantity("1.2cup");
        IngredientQuantity d = new IngredientQuantity("3/4 cup");
        IngredientQuantity e = new IngredientQuantity("2 1/2 cup");
        IngredientQuantity f = new IngredientQuantity("100 ml");

        assertThrows(IllegalArgumentException.class, () -> a.add(f));
        assertThrows(IllegalArgumentException.class, () -> b.add(f));
        assertThrows(IllegalArgumentException.class, () -> c.add(f));
        assertThrows(IllegalArgumentException.class, () -> d.add(f));
        assertThrows(IllegalArgumentException.class, () -> e.add(f));
        assertThrows(IllegalArgumentException.class, () -> f.add(a));
        assertThrows(IllegalArgumentException.class, () -> f.add(b));
        assertThrows(IllegalArgumentException.class, () -> f.add(c));
        assertThrows(IllegalArgumentException.class, () -> f.add(d));
        assertThrows(IllegalArgumentException.class, () -> f.add(e));

        assertEquals("1.25 cup", a.add(b).toString());
        assertEquals("2.2 cup", a.add(c).toString());
        assertEquals("1 3/4 cup", a.add(d).toString());
        assertEquals("3 1/2 cup", a.add(e).toString());

        assertEquals("1.25 cup", b.add(a).toString());
        assertEquals("1.45 cup", b.add(c).toString());
        assertEquals("1 cup", b.add(d).toString());
        assertEquals("2 3/4 cup", b.add(e).toString());

        assertEquals("2.2 cup", c.add(a).toString());
        assertEquals("1.45 cup", c.add(b).toString());
        assertEquals("1.95 cup", c.add(d).toString());
        assertEquals("3.7 cup", c.add(e).toString());

        assertEquals("1 3/4 cup", d.add(a).toString());
        assertEquals("1 cup", d.add(b).toString());
        assertEquals("1.95 cup", d.add(c).toString());
        assertEquals("3 1/4 cup", d.add(e).toString());

        assertEquals("3 1/2 cup", e.add(a).toString());
        assertEquals("2 3/4 cup", e.add(b).toString());
        assertEquals("3.7 cup", e.add(c).toString());
        assertEquals("3 1/4 cup", e.add(d).toString());
    }

    @Test
    public void subtract() {
        IngredientQuantity a = new IngredientQuantity("1 cup");
        IngredientQuantity b = new IngredientQuantity("0.25 cup");
        IngredientQuantity c = new IngredientQuantity("1.2cup");
        IngredientQuantity d = new IngredientQuantity("3/4 cup");
        IngredientQuantity e = new IngredientQuantity("2 1/2 cup");
        IngredientQuantity f = new IngredientQuantity("100 ml");

        assertThrows(IllegalArgumentException.class, () -> a.subtract(f));
        assertThrows(IllegalArgumentException.class, () -> b.subtract(f));
        assertThrows(IllegalArgumentException.class, () -> c.subtract(f));
        assertThrows(IllegalArgumentException.class, () -> d.subtract(f));
        assertThrows(IllegalArgumentException.class, () -> e.subtract(f));
        assertThrows(IllegalArgumentException.class, () -> f.subtract(a));
        assertThrows(IllegalArgumentException.class, () -> f.subtract(b));
        assertThrows(IllegalArgumentException.class, () -> f.subtract(c));
        assertThrows(IllegalArgumentException.class, () -> f.subtract(d));
        assertThrows(IllegalArgumentException.class, () -> f.subtract(e));

        assertEquals("0.75 cup", a.subtract(b).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> a.subtract(c).toString());
        assertEquals("1/4 cup", a.subtract(d).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> a.subtract(e).toString());

        assertThrows(NonPositiveIngredientQuantityException.class, () -> b.subtract(a).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> b.subtract(c).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> b.subtract(d).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> b.subtract(e).toString());

        assertEquals("0.2 cup", c.subtract(a).toString());
        assertEquals("0.95 cup", c.subtract(b).toString());
        assertEquals("0.45 cup", c.subtract(d).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> c.subtract(e).toString());

        assertThrows(NonPositiveIngredientQuantityException.class, () -> d.subtract(a).toString());
        assertEquals("1/2 cup", d.subtract(b).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> d.subtract(c).toString());
        assertThrows(NonPositiveIngredientQuantityException.class, () -> d.subtract(e).toString());

        assertEquals("1 1/2 cup", e.subtract(a).toString());
        assertEquals("2 1/4 cup", e.subtract(b).toString());
        assertEquals("1.3 cup", e.subtract(c).toString());
        assertEquals("1 3/4 cup", e.subtract(d).toString());
    }

    @Test
    public void asProportionOf() {
        IngredientQuantity a = new IngredientQuantity("1 cup");
        IngredientQuantity b = new IngredientQuantity("0.25 cup");
        IngredientQuantity c = new IngredientQuantity("1.2cup");
        IngredientQuantity d = new IngredientQuantity("3/4 cup");
        IngredientQuantity e = new IngredientQuantity("2 1/2 cup");
        IngredientQuantity f = new IngredientQuantity("100 ml");

        assertThrows(IllegalArgumentException.class, () -> a.asProportionOf(f));
        assertThrows(IllegalArgumentException.class, () -> b.asProportionOf(f));
        assertThrows(IllegalArgumentException.class, () -> c.asProportionOf(f));
        assertThrows(IllegalArgumentException.class, () -> d.asProportionOf(f));
        assertThrows(IllegalArgumentException.class, () -> e.asProportionOf(f));
        assertThrows(IllegalArgumentException.class, () -> f.asProportionOf(a));
        assertThrows(IllegalArgumentException.class, () -> f.asProportionOf(b));
        assertThrows(IllegalArgumentException.class, () -> f.asProportionOf(c));
        assertThrows(IllegalArgumentException.class, () -> f.asProportionOf(d));
        assertThrows(IllegalArgumentException.class, () -> f.asProportionOf(e));

        assertEquals(1, a.asProportionOf(b));
        assertEquals(1 / 1.2, a.asProportionOf(c));
        assertEquals(1, a.asProportionOf(d));
        assertEquals(0.4, a.asProportionOf(e));

        assertEquals(0.25, b.asProportionOf(a));
        assertEquals(0.25 / 1.2, b.asProportionOf(c));
        assertEquals(1.0 / 3, b.asProportionOf(d));
        assertEquals(0.1, b.asProportionOf(e));

        assertEquals(1, c.asProportionOf(a));
        assertEquals(1, c.asProportionOf(b));
        assertEquals(1, c.asProportionOf(d));
        assertEquals(0.48, c.asProportionOf(e));

        assertEquals(0.75, d.asProportionOf(a));
        assertEquals(1, d.asProportionOf(b));
        assertEquals(0.75 / 1.2, d.asProportionOf(c));
        assertEquals(0.3, d.asProportionOf(e));

        assertEquals(1, e.asProportionOf(a));
        assertEquals(1, e.asProportionOf(b));
        assertEquals(1, e.asProportionOf(c));
        assertEquals(1, e.asProportionOf(d));
    }

    @Test
    public void parseValue() {
        // valid ingredient quantity
        assertEquals("12345", IngredientQuantity.parseValue("12345").toString()); // whole number
        assertEquals("0.5", IngredientQuantity.parseValue("0.5").toString()); // decimal number
        assertEquals("1/2", IngredientQuantity.parseValue("1/2").toString()); // pure fraction
        assertEquals("1 1/2", IngredientQuantity.parseValue("1 1/2").toString()); // mixed fraction
        assertEquals("100", IngredientQuantity.parseValue("100 ml").toString()); // whole number and unit
        assertEquals("100", IngredientQuantity.parseValue("100ml").toString()); // no space between value and unit
        assertEquals("2.5", IngredientQuantity.parseValue("2.5 cups").toString()); // decimal number and unit
        assertEquals("0.5", IngredientQuantity.parseValue(".5 cups").toString()); // starts with decimal point
        assertEquals("1/2", IngredientQuantity.parseValue("1/2 cups").toString()); // pure fractions and unit
        assertEquals("2.3", IngredientQuantity.parseValue("2 3/10 cups").toString()); // denominator larger than 6
        assertEquals("1", IngredientQuantity.parseValue("1 Tablespoon").toString()); // with capital letters
        assertEquals("1", IngredientQuantity.parseValue("1 rounded tsp").toString()); // unit with spaces;
    }

    @Test
    public void parseUnit() {
        // valid ingredient quantity
        assertEquals("", IngredientQuantity.parseUnit("12345")); // whole number
        assertEquals("", IngredientQuantity.parseUnit("0.5")); // decimal number
        assertEquals("", IngredientQuantity.parseUnit("1/2")); // pure fraction
        assertEquals("", IngredientQuantity.parseUnit("1 1/2")); // mixed fraction
        assertEquals("ml", IngredientQuantity.parseUnit("100 ml")); // whole number and unit
        assertEquals("ml", IngredientQuantity.parseUnit("100ml")); // no space between value and unit
        assertEquals("cups", IngredientQuantity.parseUnit("2.5 cups")); // decimal number and unit
        assertEquals("cups", IngredientQuantity.parseUnit(".5 cups")); // starts with decimal point
        assertEquals("cups", IngredientQuantity.parseUnit("1/2 cups")); // pure fractions and unit
        assertEquals("cups", IngredientQuantity.parseUnit("2 3/5 cups")); // mixed fractions and unit
        assertEquals("Tablespoon", IngredientQuantity.parseUnit("1 Tablespoon")); // with capital letters
        assertEquals("rounded tsp", IngredientQuantity.parseUnit("1 rounded tsp ")); // unit with spaces;
    }

    @Test
    public void toString_validIngredientQuantity_returnsStringRepresentation() {
        // valid ingredient quantity
        assertEquals("12345", new IngredientQuantity("12345").toString()); // whole number
        assertEquals("0.5", new IngredientQuantity("0.5").toString()); // decimal number
        assertEquals("1/2", new IngredientQuantity("1/2").toString()); // pure fraction
        assertEquals("1 1/2", new IngredientQuantity("1 1/2").toString()); // mixed fraction
        assertEquals("100 ml", new IngredientQuantity("100 ml").toString()); // whole number and unit
        assertEquals("100 ml", new IngredientQuantity("100ml").toString()); // no space between value and unit
        assertEquals("2.5 cups", new IngredientQuantity("2.5 cups").toString()); // decimal number and unit
        assertEquals("0.5 cups", new IngredientQuantity(".5 cups").toString()); // starts with decimal point
        assertEquals("1/2 cups", new IngredientQuantity("1/2 cups").toString()); // pure fractions and unit
        assertEquals("2.3 cups", new IngredientQuantity("2 3/10 cups").toString()); // mixed fractions and unit
        assertEquals("1 Tablespoon", new IngredientQuantity("1 Tablespoon").toString()); // with capital letters
        assertEquals("1 rounded tsp", new IngredientQuantity("1 rounded tsp ").toString()); // unit with spaces;
    }

    @Test
    public void equals() {
        IngredientQuantity testIngredientQuantity = new IngredientQuantity("100 ml");

        // same quantity -> returns true
        assertTrue(testIngredientQuantity.equals(new IngredientQuantity("100 ml")));

        // same object -> returns true
        assertTrue(testIngredientQuantity.equals(testIngredientQuantity));

        // same quantity with no whitespace between value and unit -> returns true
        assertTrue(testIngredientQuantity.equals(new IngredientQuantity("100ml")));

        // same quantity with trailing whitespace -> returns true
        assertTrue(testIngredientQuantity.equals(new IngredientQuantity("100 ml ")));

        // null -> returns false
        assertFalse(testIngredientQuantity.equals(null));

        // different ingredient quantity -> returns false
        assertFalse(testIngredientQuantity.equals(new IngredientQuantity("200 g")));

        // different ingredient quantity value -> returns false
        assertFalse(testIngredientQuantity.equals(new IngredientQuantity("200 ml")));

        // different ingredient quantity unit -> returns false
        assertFalse(testIngredientQuantity.equals(new IngredientQuantity("100 g")));

        // different ingredient quantity unit -> returns false
        assertFalse(testIngredientQuantity.equals(new IngredientQuantity("100")));
    }
}

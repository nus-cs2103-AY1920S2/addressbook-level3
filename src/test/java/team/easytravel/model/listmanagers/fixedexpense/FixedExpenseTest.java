package team.easytravel.model.listmanagers.fixedexpense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FixedExpenseTest {

    @Test
    public void constructorInvalidNameThrowsIllegalArgumentException() {
        String invalidAmount = "";
        String invalidDescription = "";
        String invalidCategory = "";
        assertThrows(IllegalArgumentException.class, () -> new FixedExpense(new Amount(invalidAmount),
                new Description(invalidDescription), new FixedExpenseCategory(invalidCategory)));
    }

    @Test
    public void constructorHasOneInvalidNameThrowsIllegalArgumentException() {
        String invalidAmount = "";
        String description = "Food";
        String category = "Food item";
        assertThrows(IllegalArgumentException.class, () -> new FixedExpense(new Amount(invalidAmount),
                new Description(description), new FixedExpenseCategory(category)));
    }

    @Test
    public void getAmount() {
        // Correct case
        assertEquals(new Amount("100"), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("transport")).getAmount());

        //Different case
        assertNotEquals(new Amount("500"), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("transport")).getAmount());
    }

    @Test
    public void getDescription() {
        // Correct case
        assertEquals(new Description("Test"), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("accommodations")).getDescription());

        //Different case
        assertNotEquals(new Description("50p"), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("accommodations")).getDescription());

    }

    @Test
    public void getCategory() {
        // Correct case
        assertEquals(new FixedExpenseCategory("activities"), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("activities")).getFixedExpenseCategory());

        //Different case
        assertNotEquals(new FixedExpenseCategory("transport"), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("activities")).getFixedExpenseCategory());
    }

    @Test
    public void testEquals() {
        //Same case
        assertEquals(new FixedExpense(new Amount("100"), new Description("Test"),
                new FixedExpenseCategory("activities")), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("activities")));

        //Different case
        assertNotEquals(new FixedExpense(new Amount("200"), new Description("Testy"),
                new FixedExpenseCategory("transport")), new FixedExpense(new Amount("100"),
                new Description("Test"), new FixedExpenseCategory("transport")));
    }

    @Test
    public void testHashCode() {
        //Same case
        assertEquals(new FixedExpense(new Amount("12345"),
                        new Description("Foody"), new FixedExpenseCategory("transport")).hashCode(),
                new FixedExpense(new Amount("12345"), new Description("Foody"),
                        new FixedExpenseCategory("transport")).hashCode());
        //Different Case
        assertNotEquals(new FixedExpense(new Amount("123456"),
                        new Description("Food"), new FixedExpenseCategory("accommodations")).hashCode(),
                new FixedExpense(new Amount("12345"), new Description("Foody"),
                        new FixedExpenseCategory("accommodations")).hashCode());
    }

    @Test
    public void testToString() {
        //Same case
        FixedExpense fixedExpense = new FixedExpense(new Amount("100"),
                new Description("Food"), new FixedExpenseCategory("others"));
        assertEquals("Fixed Expense Entry - Description: " + fixedExpense.getDescription().toString()
                        + " Amount: " + fixedExpense.getAmount().toString()
                        + " Category: " + fixedExpense.getFixedExpenseCategory().toString(),
                "Fixed Expense Entry - Description: " + fixedExpense.getDescription().toString()
                        + " Amount: " + fixedExpense.getAmount().toString()
                        + " Category: " + fixedExpense.getFixedExpenseCategory().toString());

        //Different case
        assertNotEquals("Fixed Expense Entry - Description: " + fixedExpense.getAmount().toString()
                        + " Amount: " + fixedExpense.getDescription().toString()
                        + " Category: " + fixedExpense.getFixedExpenseCategory().toString(),
                "Fixed Expense Entry - Description: " + fixedExpense.getDescription().toString()
                        + " Amount: " + fixedExpense.getAmount().toString()
                        + " Category: " + fixedExpense.getFixedExpenseCategory().toString());

    }

    @Test
    public void testisSame() {

        FixedExpense fixedExpense = new FixedExpense(new Amount("100"), new Description("Test"),
                new FixedExpenseCategory("others"));

        FixedExpense differentFixedExpense = new FixedExpense(new Amount("100"), new Description("Test"),
                new FixedExpenseCategory("accommodations"));

        assertTrue(fixedExpense.isSame(fixedExpense));
        assertFalse(fixedExpense.isSame(differentFixedExpense));
    }
}

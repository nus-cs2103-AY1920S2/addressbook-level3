package team.easytravel.model.trip;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.easytravel.testutil.Assert;

public class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudget_throwsIllegalArgumentException() {
        Integer invalidBudget = 0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null Budget
        Assert.assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        // invalid Budget
        assertFalse(Budget.isValidBudget(-000000)); // negative 0s
        assertFalse(Budget.isValidBudget(-2)); // spaces only
        assertFalse(Budget.isValidBudget(999999999)); // very big number

        // valid Budget
        assertTrue(Budget.isValidBudget(911)); // exactly 3 numbers
        assertTrue(Budget.isValidBudget(000000001)); //extra 0s

    }
}

package csdev.couponstash.model.element;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.util.DateUtil;


public class MonthViewTest {
    private final MonthView monthView = new MonthView("8-2020");


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MonthView(null));
    }

    @Test
    public void constructor_invalidMonthView_throwsIllegalArgumentException() {
        String invalidYearMonth = "22-2222";
        assertThrows(IllegalArgumentException.class, () -> new MonthView(invalidYearMonth));
    }

    @Test
    public void isValidYearMonth() {
        // null usage
        assertThrows(NullPointerException.class, () -> DateUtil.isValidYearMonth(null));

        // invalid usage
        assertFalse(DateUtil.isValidYearMonth(" ")); // spaces only
        assertFalse(DateUtil.isValidYearMonth("")); // empty string
        assertFalse(DateUtil.isValidYearMonth("^")); // only non-alphanumeric characters
        assertFalse(DateUtil.isValidYearMonth("abc*")); // contains non-alphanumeric characters
        assertFalse(DateUtil.isValidYearMonth("22")); // wrong format


        // valid usage
        assertTrue(DateUtil.isValidYearMonth("8-2020")); // in M-YYYY format
        assertTrue(DateUtil.isValidYearMonth("10-2020")); // in M-YYYY format
    }

    @Test
    public void equals() {
        MonthView otherMonthView = new MonthView("8-2020");

        //Same Object
        assertEquals(monthView, monthView);

        //Different object, Same Year Month
        assertEquals(monthView, otherMonthView);

        //Different object, different YearMonth
        otherMonthView = new MonthView("9-2020");
        assertNotEquals(monthView, otherMonthView);
    }
}

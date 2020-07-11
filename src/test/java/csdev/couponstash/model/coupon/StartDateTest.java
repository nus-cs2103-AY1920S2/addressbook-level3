package csdev.couponstash.model.coupon;

import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_BOB;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class StartDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    @Test
    public void constructor_invalidStartDate_throwsIllegalArgumentException() {
        String invalidStartDate = " ";
        assertThrows(IllegalArgumentException.class, () -> new StartDate(invalidStartDate));
    }

    @Test
    public void equals() {
        StartDate amySd = new StartDate(VALID_START_DATE_AMY);
        StartDate bobSd = new StartDate(VALID_START_DATE_BOB);

        //same Object -> returns true
        assertTrue(amySd.equals(amySd));

        //same value -> returns true
        assertTrue(amySd.value.equals(amySd.value));

        //same LocalDate -> returns true
        assertTrue(amySd.date.equals(amySd.date));

        //null -> returns false
        assertFalse(amySd.equals(null));

        //different type -> returns false
        assertFalse(amySd.equals(5));

        //different ExpiryDate -> returns false
        assertFalse(amySd.equals(bobSd));


        //same date, different String -> returns true
        amySd = new StartDate("1-1-2020");
        bobSd = new StartDate("01-01-2020");
        assertTrue(amySd.equals(bobSd));
    }
}

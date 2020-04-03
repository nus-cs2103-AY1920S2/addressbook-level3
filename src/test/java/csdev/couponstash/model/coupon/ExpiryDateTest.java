package csdev.couponstash.model.coupon;

import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_AMY;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_EXPIRY_DATE_BOB;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExpiryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpiryDate(null));
    }

    @Test
    public void constructor_invalidExpiryDate_throwsIllegalArgumentException() {
        String invalidExpiryDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ExpiryDate(invalidExpiryDate));
    }

    @Test
    public void equals() {
        ExpiryDate amyEd = new ExpiryDate(VALID_EXPIRY_DATE_AMY);
        ExpiryDate bobEd = new ExpiryDate(VALID_EXPIRY_DATE_BOB);

        //same Object -> returns true
        assertTrue(amyEd.equals(amyEd));

        //same value -> returns true
        assertTrue(amyEd.value.equals(amyEd.value));

        //same LocalDate -> returns true
        assertTrue(amyEd.date.equals(amyEd.date));

        //null -> returns false
        assertFalse(amyEd.equals(null));

        //different type -> returns false
        assertFalse(amyEd.equals(5));

        //different ExpiryDate -> returns false
        assertFalse(amyEd.equals(bobEd));


        //same date, different String -> returns true
        amyEd = new ExpiryDate("1-1-2020");
        bobEd = new ExpiryDate("01-01-2020");
        assertTrue(amyEd.equals(bobEd));
    }
}

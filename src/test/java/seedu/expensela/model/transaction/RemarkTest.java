package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RemarkTest {

    @Test
    void isValidRemark() {

//        // invalid remark
//        assertFalse(Remark.isValidRemark(" ")); // spaces only
//        assertFalse(Remark.isValidRemark("^")); // only non-alphanumeric characters
//        assertFalse(Remark.isValidRemark("peter*")); // contains non-alphanumeric characters

        // valid remark
        assertTrue(Remark.isValidRemark("peter jack")); // alphabets only
        assertTrue(Remark.isValidRemark("12345")); // numbers only
        assertTrue(Remark.isValidRemark("peter the 2nd")); // alphanumeric characters
        assertTrue(Remark.isValidRemark("Capital Tan")); // with capital letters
        assertTrue(Remark.isValidRemark("David Roger Jackson Ray Jr 2nd")); // long remarks
    }
}

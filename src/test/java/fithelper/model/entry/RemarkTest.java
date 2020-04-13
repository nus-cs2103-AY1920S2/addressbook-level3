package fithelper.model.entry;

import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void isValidRemark() {
        // null remark
        assertFalse(Remark.isValidRemark(null));

        // valid remark
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark("the food is reasonably priced")); // alphabets only
        assertTrue(Remark.isValidRemark("I had a good meal")); // with capital letters
    }
}

package fithelper.model.entry;

import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void isValidStatus() {
        // null status
        //assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("done")); // lower case done
        assertFalse(Status.isValidStatus("undone")); // lonwer case undone
        assertFalse(Status.isValidStatus("Not sure")); // other undefined status

        // valid status
        assertTrue(Status.isValidStatus("Done")); // done status
        assertTrue(Status.isValidStatus("Undone")); // undone status
    }
}

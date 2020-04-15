package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "done";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("done")); // spaces only

        // valid status
        assertTrue(Status.isValidStatus("Completed"));
        assertTrue(Status.isValidStatus("comPleTed"));
        assertTrue(Status.isValidStatus("COMPLETED"));

        assertTrue(Status.isValidStatus("Uncompleted"));
        assertTrue(Status.isValidStatus("unComplEtEd"));
        assertTrue(Status.isValidStatus("UNCOMPLETED"));
    }
}

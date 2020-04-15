package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WorkloadTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Workload(null));
    }

    @Test
    public void isValidWorkload() {
        // null workload
        assertThrows(NullPointerException.class, () -> Workload.isValidWorkload(null));

        // invalid workload
        assertFalse(Workload.isValidWorkload(" ")); // spaces only
        assertFalse(Workload.isValidWorkload(" efwefewf ")); // contains letters
        assertFalse(Workload.isValidWorkload("-1")); // negative workload
        assertFalse(Workload.isValidWorkload("34.58")); // Should be rounded off to the nearest half an hour
        assertFalse(Workload.isValidWorkload("45.6")); // Should be rounded off to the nearest half an hour

        // valid workload
        assertTrue(Workload.isValidWorkload("45.5"));
        assertTrue(Workload.isValidWorkload("40"));
        assertTrue(Workload.isValidWorkload("100"));
    }

}

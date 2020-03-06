package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalActivities.DEADLINE;

import org.junit.jupiter.api.Test;

/**
 * To test the UniqueActivity class.
 */
class UniqueActivityListTest {

    private final UniqueActivityList uniqueActivityList = new UniqueActivityList();

    @Test
    public void contains_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueActivityList.contains(null));
    }

    @Test
    public void contains_activityNotInList_returnsFalse() {
        assertFalse(uniqueActivityList.contains(DEADLINE));
    }

    @Test
    public void contains_activityInList_returnsTrue() {
        uniqueActivityList.add(DEADLINE);
        assertTrue(uniqueActivityList.contains(DEADLINE));
    }
}

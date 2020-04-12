package nasa.model.activity;

import static nasa.testutil.TypicalActivities.DEADLINE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class UniqueDeadlineListTest {

    private final UniqueDeadlineList uniqueDeadlineList = new UniqueDeadlineList();
    private final UniqueDeadlineList newUniqueDeadlineList = new UniqueDeadlineList();


    @Test
    public void contains_nullActivity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeadlineList.contains(null));
    }

    @Test
    public void contains_activityNotInList_returnsFalse() {
        assertFalse(uniqueDeadlineList.contains(DEADLINE));
    }

    @Test
    public void contains_activityInList_returnsTrue() {
        uniqueDeadlineList.add(DEADLINE);
        assertTrue(uniqueDeadlineList.contains(DEADLINE));
    }

    @Test
    public void remove_test() {
        uniqueDeadlineList.add((Deadline) DEADLINE);
        uniqueDeadlineList.remove((Deadline) DEADLINE);
        assertFalse(uniqueDeadlineList.contains((Deadline) DEADLINE));
    }

    @Test
    public void checkUnmodifiableList_test() {
        uniqueDeadlineList.add((Deadline) DEADLINE);
        uniqueDeadlineList.add((Deadline) DEADLINE);
        assertTrue(uniqueDeadlineList.asUnmodifiableObservableList().size() == 2);
    }

    @Test
    public void modifyUnmodifiableList_test() {
        uniqueDeadlineList.add((Deadline) DEADLINE);
        ObservableList<Deadline> temp = uniqueDeadlineList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> temp.add((Deadline) DEADLINE));
    }

}

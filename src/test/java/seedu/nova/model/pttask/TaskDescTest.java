package seedu.nova.model.pttask;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.nova.model.progresstracker.TaskDesc;

public class TaskDescTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskDesc(null));
    }

    @Test
    public void isValidTaskDesc() {
        assertFalse(TaskDesc.isValidTaskDesc(""));

        assertTrue(TaskDesc.isValidTaskDesc("taskDesc"));
    }
}

package seedu.address.model.ModelObjectTest;

import org.junit.jupiter.api.Test;
import seedu.address.model.modelObjectTags.CompositeID;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelProgress.Progress;

import static seedu.address.testutil.Assert.assertThrows;

public class ProgressTest {
    @Test
    public void isValidProgress() {
        // null progress
        assertThrows(NullPointerException.class, () -> new Progress(null));
        // inValidCompositeID
        assertThrows(NullPointerException.class, () -> new Progress(new CompositeID(null, null)));
        // inValidCompositeID
        assertThrows(NullPointerException.class, () -> new Progress(new CompositeID(new ID("1"), null)));
        // inValidCompositeID
        assertThrows(NullPointerException.class, () -> new Progress(new CompositeID(null, new ID("1"))));
    }
}

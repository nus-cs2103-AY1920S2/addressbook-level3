package seedu.address.model.modelObjectTags;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalProgress.*;

public class CompositeIDTest {
    @Test
    public void equals_sameCompositeID_success() throws CommandException {
        CompositeID first = CompositeID_S1_A1;
        CompositeID second = new CompositeID(assignment100, student1);

        assertEquals(first, second);
    }

    @Test
    public void equals_differentCompositeID_failure() {
        CompositeID first = CompositeID_S1_A1;
        CompositeID second = CompositeID_S1_A2;
        assertNotEquals(first, second);
    }
}
